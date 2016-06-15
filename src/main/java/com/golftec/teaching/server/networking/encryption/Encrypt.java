package com.golftec.teaching.server.networking.encryption;

import com.golftec.teaching.server.networking.util.ByteObjectCommon;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static java.util.Calendar.*;

/**
 * Note, this object will only work until 20 Nov 2286.
 * Until then, a Java timestamp is exactly 13 digits long. This object
 * expects timestamps to be 13 digits long and not more or less. So, in
 * 267 years, this object needs to be updated...
 * <p>
 * The epoch for the times sent to and from this object need to be the time as of
 * midnight, 01 January 1970. The time value should be the difference in milliseconds
 * between that time and now, whenever now is.
 *
 * @author Al Wells
 */
@SuppressWarnings("MagicConstant")
public class Encrypt {

    //This is a secret key used by the server and any system sending to or receiving data from
    //the client. For AES padding, must be 16 bytes...
    private static final String SECRET_KEY = "L!k325w!Ng0n4sTr";
    private static final String ALGORITHM = "AES";
    private static final String ALGORITHM_NO_PADDING = "AES/ECB/NoPadding";
    private static final String SALT = "kKju9b1^++heu=";
    private static final Cipher cipherForEncrypting;
    private static final Cipher cipherForDecrypting;
    private static final Cipher cipherForDecryptingNoPadding;

    static {
        Key key = generateKey();
        cipherForEncrypting = initCipher(Cipher.ENCRYPT_MODE, ALGORITHM, key);
        cipherForDecrypting = initCipher(Cipher.DECRYPT_MODE, ALGORITHM, key);
        //NOTE: decrypting use a more forgiveness algorithm so it can ALSO decrypt stuff from PHP Aes
        cipherForDecryptingNoPadding = initCipher(Cipher.DECRYPT_MODE, ALGORITHM_NO_PADDING, key);
    }

    /**
     * This method will return an encrypted string. Decrypt the string and then
     * get the time (the first 13 bytes). Then build the salt from that time
     * and remove the salt from the decrypted string to read back the string.
     *
     * @param rawString the string you want to read.
     * @return an encrypted string
     * @throws Exception if anything goes wrong.
     */
    public static String encryptString(String rawString) throws Exception {
        Long time = System.currentTimeMillis();
        String valueToEnc = "" + time + getSalt(time) + rawString;
        return justEncryptString(valueToEnc);
    }

    /**
     * just do the encryption, don't care about time
     */
    private static String justEncryptString(String valueToEnc) throws Exception {
        byte[] encValue = justEncrypt(valueToEnc.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.encodeBase64(encValue), StandardCharsets.UTF_8);
    }

    /**
     * Method will return an encrypted array of bytes representing an object. The first 13 bytes of the
     * byte[] will be the timestamp needed to decrypt this object. To decrypt, remove the
     * 13 bytes then get the salt then remove the salt then build the object from the left
     * over bytes.
     *
     * @param rawObject an object to encrypt
     * @return bytes representing the encrypted object
     * @throws Exception if anything goes wrong.
     */
    public static byte[] encryptObject(Serializable rawObject) throws Exception {
        try (ByteArrayOutputStream encStream = new ByteArrayOutputStream()) {
            Long time = System.currentTimeMillis();
            encStream.write(("" + time + getSalt(time)).getBytes());
            byte[] encryptValue = ByteObjectCommon.getObjectBytes(rawObject);
            encStream.write(encryptValue != null ? encryptValue : new byte[0]);
            return justEncrypt(encStream.toByteArray());
        }
    }

    /**
     * The very core function of encrypting process.
     * Just encrypt byte[], return byte[], don't care about other stuff
     */
    private static byte[] justEncrypt(byte[] bytesToBeEncrypted) throws Exception {
        return cipherForEncrypting.doFinal(bytesToBeEncrypted);
    }

    /**
     * Method will decrypt any string encrypted with this object or that encrypts following the rules of this
     * object. To be able to be properly decrypted, the string must have been encrypted with:
     * <p>
     * systemTime in MS (13 digits) + salt (per the salt algorithm of this object) + string.
     *
     * @param encryptedString a properly encrypted String
     * @return a decrypted plain text String
     * @throws Exception if the String was not properly encrypted.
     */
    public static String decryptString(String encryptedString) throws Exception {
        String sDecryptedValue = justDecryptString(encryptedString).trim();
        long t = getTime(sDecryptedValue);
        return sDecryptedValue.substring(getSalt(t).length() + 13);
    }

    /**
     * Just decrypt the input string, don't care about the time.
     * NOTE: With the current usage, it should have been returning byte[],
     * but in order to keep consistent with the justEncryptString and other API, I let it return String
     */
    private static String justDecryptString(String encryptedString) throws Exception {
        byte[] decodedValue = Base64.decodeBase64(encryptedString.getBytes(StandardCharsets.UTF_8));
        return new String(justDecryptNoPadding(decodedValue), StandardCharsets.UTF_8);
    }

    /**
     * The very core function of decrypting process.
     * Just decrypt the input bytes, return bytes
     */
    private static byte[] justDecrypt(byte[] bytesToBeDecrypted) throws Exception {
        return cipherForDecrypting.doFinal(bytesToBeDecrypted);
    }

    private static byte[] justDecryptNoPadding(byte[] bytesToBeDecrypted) throws Exception {
        return cipherForDecryptingNoPadding.doFinal(bytesToBeDecrypted);
    }

    /**
     * This method will return a decrypted object assuming the object was properly encrypted
     * using the algorithmic rules of this object.
     *
     * @param encryptedObject the object to decrypt
     * @return a properly decrypted object.
     * @throws Exception if the object was not encrypted properly.
     */
    public static Object decryptObject(byte[] encryptedObject) throws Exception {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            byte[] decValue = justDecrypt(encryptedObject);
            Long t = getTime(decValue);
            stream.write(decValue, getSalt(t).getBytes().length + 13, decValue.length - (getSalt(t).getBytes().length + 13));
            return ByteObjectCommon.getObject(stream.toByteArray());
        }
    }

    //private methods...
    private static String getSalt(long time) throws Exception {
        Calendar c = getInstance(TimeZone.getTimeZone("GMT-6:00"));
        c.setTimeInMillis(time);
        String hour = "" + c.get(HOUR_OF_DAY);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        String day = "" + c.get(DAY_OF_MONTH);
        if (day.length() == 1) {
            day = "0" + day;
        }
        String weekDay = c.getDisplayName(DAY_OF_WEEK, LONG_FORMAT, Locale.US);
        String month = c.getDisplayName(MONTH, LONG_FORMAT, Locale.US);
        int year = c.get(YEAR);
        String salt = "" + year + SALT + month + day + year + hour + weekDay + SALT + weekDay;
//        System.out.println("Server: salt before hashed: " + salt);
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();
        String hex = new HexBinaryAdapter().marshal(digest).toLowerCase();
//        System.out.println("Server: Salt bytes: " + hex);
        // randomize it somehow
        hex = hex.replaceAll("[abcdef23456789]", "");
//        String toReturn = new String(digest, "UTF-8");
//        System.out.println("Server: Salt: " + toReturn);
        return hex;
    }

    private static Key generateKey() {
        try {
            return new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
    }

    private static Cipher initCipher(int mode, String algorithm, Key key) {
        try {
            Cipher cipherForEncrypting = Cipher.getInstance(algorithm);
            cipherForEncrypting.init(mode, key);
            return cipherForEncrypting;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * first 13 byte is supposed to be a long time value
     */
    private static long getTime(String encryptedString) throws Exception {
        return getTime(encryptedString.getBytes());
    }

    /**
     * first 13 byte is supposed to be a long time value
     */
    private static long getTime(byte[] encryptedBytes) throws Exception {
        byte[] time = new byte[13];
        System.arraycopy(encryptedBytes, 0, time, 0, time.length);
        return Long.parseLong(new String(time, StandardCharsets.UTF_8));
    }
}
