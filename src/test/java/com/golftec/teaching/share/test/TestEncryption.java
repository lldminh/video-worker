package com.golftec.teaching.share.test;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.server.networking.encryption.Encrypt;
import com.golftec.teaching.server.networking.request.LoginRequestData;
import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.server.networking.type.GTMethod;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TestEncryption {

    @Test
    public void testStringEncryptDecrypt() {
        String stringToEncrypt = "This is a test string";
        try {
            String encryptedString = Encrypt.encryptString(stringToEncrypt);
            System.out.println(encryptedString);
            String decryptedString = Encrypt.decryptString(encryptedString);
            System.out.println(decryptedString);
            assertTrue(stringToEncrypt.equals(decryptedString));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectEncryptDecrypt() {
        try {
            Coach coach = new Coach();
            coach.name = "Golf Coach";
            byte[] encryptedBytes = Encrypt.encryptObject(coach);
            Coach decryptedCoach = (Coach) Encrypt.decryptObject(encryptedBytes);
            System.out.println("Decrypted coach name is: " + decryptedCoach.name);
            assertTrue(coach.name.equals(decryptedCoach.name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testObjectEncryptDecrypt2() throws Exception {
        LoginRequestData loginRequestData = new LoginRequestData("rklados", "r475869k");
        String installationId = "TestMachine";
        Request request = new Request(GTMethod.Login.id, loginRequestData, installationId, GTUtil.uuid());
        byte[] encryptedBytes = Encrypt.encryptObject(request);
        Request decryptedRequest = (Request) Encrypt.decryptObject(encryptedBytes);
        assertNotNull(decryptedRequest);
        Optional<LoginRequestData> opDecryptedLoginData = GTUtil.parseDataSafely(decryptedRequest.data, LoginRequestData.class);
        assertTrue(opDecryptedLoginData.isPresent());
        LoginRequestData decryptedLoginRequestData = opDecryptedLoginData.get();
        assertEquals("rklados", decryptedLoginRequestData.username);
        assertEquals("r475869k", decryptedLoginRequestData.password);
    }
}
