package com.golftec.teaching.common;

import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.MotionHistory;
import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.view.EnvironmentView;
import com.golftec.teaching.view.LessonView;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Created by Hoang on 2015-04-16.
 */
@SuppressWarnings("unused")
public final class GTUtil {

    /**
     * Basic, no custom type adapter except for the datetime
     */
    public static final Gson gsonBasic = new GsonBuilder().registerTypeAdapter(Date.class, new UtcDateTypeAdapter())
                                                          .registerTypeAdapter(DateTime.class, new JodaDateTimeAdapter())
                                                          .setExclusionStrategies(new AnnotationExclusionStrategy())
                                                          .addSerializationExclusionStrategy(new AnnotationSerializationExclusionStrategy())
                                                          .disableHtmlEscaping()
                                                          .create();
    /**
     * The gson object that is specific for parsing GTShape objects
     */
    public static final Gson gsonForGTShape = new GsonBuilder().registerTypeAdapter(Date.class, new UtcDateTypeAdapter())
                                                               .registerTypeAdapter(DateTime.class, new JodaDateTimeAdapter())
                                                               .registerTypeAdapter(GTShape.class, new GTShapeTypeAdapter())
                                                               .registerTypeAdapter(com.golftec.teaching.videoUtil.drawingToolEx.GTShape.class, new GTShapeExTypeAdapter())
                                                               .setExclusionStrategies(new AnnotationExclusionStrategy())
                                                               .addSerializationExclusionStrategy(new AnnotationSerializationExclusionStrategy())
                                                               .disableHtmlEscaping()
                                                               .create();

    /**
     * The main gson object for parsing almost everything in the system
     */
    public static final Gson gsonMain = new GsonBuilder().registerTypeAdapter(Date.class, new UtcDateTypeAdapter())
                                                         .registerTypeAdapter(DateTime.class, new JodaDateTimeAdapter())
                                                         .registerTypeAdapter(AnnotationHistory.class, new AnnotationHistoryAdapter())
                                                         .registerTypeAdapter(VideoHistory.class, new VideoHistoryAdapter())
                                                         .registerTypeAdapter(MotionHistory.class, new MotionHistoryAdapter())
                                                         .registerTypeAdapter(ToolBoardHistory.class, new ToolBoardHistoryAdapter())
                                                         .registerTypeHierarchyAdapter(GTShape.class, new GTShapeTypeHierarchyAdapter())
                                                         .registerTypeHierarchyAdapter(com.golftec.teaching.videoUtil.drawingToolEx.GTShape.class, new GTShapeExTypeHierarchyAdapter())
                                                         .setExclusionStrategies(new AnnotationExclusionStrategy())
                                                         .addSerializationExclusionStrategy(new AnnotationSerializationExclusionStrategy())
                                                         .disableHtmlEscaping()
                                                         .create();
    public static final Type shapeListType = new com.google.common.reflect.TypeToken<ArrayList<com.golftec.teaching.videoUtil.drawingToolEx.GTShape>>() {}.getType();
    private static final Logger log = LoggerFactory.getLogger(GTUtil.class);
    private static final Random random = new Random();

    public static String nulTerminated(String input) {
        return Strings.nullToEmpty(input) + '\0';
    }

    /**
     * Call the task in another thread of a prepared thread-pool.
     * To block and wait for the result of the task, call future.get()
     */
    public static <T> Future<T> async(Callable<T> task) {
        return GTThreadPool.submit(task);
    }

    public static String base64(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64(String str) {
        return Base64.decodeBase64(str);
    }

    /**
     * calculate total months by converting 1 year = 12 months
     * Ex: Jun 2014 = 2014 * 12 + 6
     *
     * @param year        the year to calculate
     * @param monthOfYear from 1 to 12 (index 1)
     * @return year * 12 + monthOfYear
     */
    public static int calTotalMonths(int year, int monthOfYear) {
        return year * 12 + monthOfYear;
    }

    private static String convertEncoding(String originalString, Charset from, Charset to) {
        try {
            byte originalBytes[] = originalString.getBytes(from);
            return new String(originalBytes, to);
        } catch (Exception e) {
            // ignored
        }
        return originalString;
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String convertFromLatin1ToUtf8(String originalString) {
        return convertEncoding(originalString, StandardCharsets.ISO_8859_1, StandardCharsets.UTF_8);
    }

    /**
     * @param timezoneOffset time unit for this is hour, it can be <= 0 and do not have to be round integer
     */
    public static DateTime convertTime(DateTime original, float timezoneOffset) {
        return original.plusMinutes((int) (timezoneOffset * 60));
    }

    /**
     * Parse json based on common message format (all part except the "data"). Generic version
     *
     * @param json  raw data from caller (often webSocket call from javascript)
     * @param clazz type of the return object
     * @return a BaseRequestMessage object that contain most data parsed, except the field "data", which is still plain json string
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return gsonMain.fromJson(json, clazz);
        } catch (Exception e) {
            log.info("ERROR parsing json: {}", json);
        }

        // in case of exception
        return null;
    }

    public static <T> T fromJson(String json, Type clazz) {
        return gsonMain.fromJson(json, clazz);
    }

    public static SecretKey generateSecretKey(int keySize, int iterations, String salt, String passphrase) {
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex(salt), iterations, keySize);
            return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        } catch (Exception e) {
            log.error("", e);
        }

        return null;
    }

    /**
     * safely get a random item from a list
     */
    public static <T> T randomItemFromList(List<T> inputList) {
        return inputList.get(positiveRandom(inputList.size()));
    }

    public static <T> T randomItemFromList(T[] inputList) {
        return inputList[positiveRandom(inputList.length)];
    }

    public static String getRemoteIp(InetSocketAddress remoteSocketAddress) {
        if (remoteSocketAddress == null) {
            return "";
        }

        if (remoteSocketAddress.isUnresolved()) {
            return Strings.nullToEmpty(remoteSocketAddress.getHostName());
        }

        InetAddress address = remoteSocketAddress.getAddress();
        if (address == null) {
            return "";
        }

        return Strings.nullToEmpty(address.getHostAddress());
    }

    public static byte[] hex(String str) throws DecoderException {
        return Hex.decodeHex(str.toCharArray());
    }

    public static String hex(byte[] bytes) {
        return Hex.encodeHexString(bytes);
    }

    public static boolean isFileUpToDate(String fileFullPath, DateTime modified) {
        File file = new File(fileFullPath);
        if (!file.exists()) {
//            logger.info("isFileUpToDate: file not exists: {}", fileFullPath);
            return false;
        }

        if (file.length() <= 0) {
//            logger.info("isFileUpToDate: file size is invalid (<=0): {}", fileFullPath);
            return false;
        }

        DateTime fileLastModified = new DateTime(file.lastModified());
        Duration duration = new Duration(fileLastModified, modified);
        //noinspection RedundantIfStatement
        if (duration.getStandardMinutes() > 1) {
            // 1 minutes passed since the file modification, and the asset record is updated, so the file is no longer up-to-date
//            logger.info("isFileUpToDate: file is older than 1min: {}", fileFullPath);
            return false;
        }

        return true;
    }

    public static String normalizeFileName(String fileName) {
        try {
            String validName = Strings.nullToEmpty(fileName);
            validName = convertFromLatin1ToUtf8(validName);
            validName = validName.replaceAll("\'", "");
            validName = validName.replaceAll("\\\\", "");
            return validName;
        } catch (Exception ignored) {
        }
        return fileName;
    }

    /**
     * To use with generic list, please see: http://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type/5554296#5554296
     */
    public static <T> T parseData(Object data, Type clazz) {
        //TODO: should have a better implementation, but this is working now
        if (data instanceof String) {
            return gsonMain.fromJson((String) data, clazz);
        }
        String tempJson = gsonMain.toJson(data);
        return gsonMain.fromJson(tempJson, clazz);
    }

    public static <T> T parseData(Object data, Class<T> clazz) {
        return parseData(data, (Type) clazz);
    }

    public static <T> Optional<T> parseDataSafely(Object data, Class<T> clazz) {
        return parseDataSafely(data, (Type) clazz);
    }

    public static <T> Optional<T> parseDataSafely(Object data, Type type) {
        try {
            return Optional.ofNullable(parseData(data, type));
        } catch (Exception ex) {
            log.error("", ex);
        }
        return Optional.empty();
    }

    public static DateTime parseDateTimeSafely(String input, DateTime defaultValue) {
        if (Strings.isNullOrEmpty(input)) {
            return defaultValue;
        }
        for (String pattern : GTConstants.DATE_TIME_FORMAT_LIST) {
            try {
                return DateTime.parse(input, DateTimeFormat.forPattern(pattern));
            } catch (Exception ignored) { }
        }
        return defaultValue;
    }

    public static DateTime parseDateTimeSafely(String input, DateTimeFormatter format, DateTime defaultValue) {
        if (Strings.isNullOrEmpty(input)) {
            return defaultValue;
        }
        try {
            DateTime dateTime = format.parseDateTime(input);
            return dateTime;
        } catch (Exception ignored) {
            log.error("", ignored);
        }

        return defaultValue;
    }

    public static DateTime convertToDateTimeSafely(Date input, DateTime defaultValue) {
        if (input == null) {
            return defaultValue;
        }

        try {
            return new DateTime(input);
        } catch (Exception ignored) { }

        return defaultValue;
    }

    public static double parseDoubleSafely(String s) {
        try {
            //noinspection ConstantConditions
            return Doubles.tryParse(s);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int parseIntSafely(String s) {
        try {
            return Ints.tryParse(s);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * guarantee to return a positive integer value
     */
    public static int positiveRandom() {
        return positiveRandom(Integer.MAX_VALUE);
    }

    /**
     * return a random boolean value
     */
    public static boolean randomBool() {
        return random.nextBoolean();
    }

    /**
     * guarantee to return a >= 0 and a < maxValExclusive, integer value.
     * Remember that 'maxValExclusive' is exclusive
     */
    public static int positiveRandom(int maxValExclusive) {
        return randomInRange(0, maxValExclusive);
    }

    /**
     * guarantee to return a >= from and a < to, integer value.
     * Remember that 'to' is exclusive.
     * <p>
     * http://stackoverflow.com/questions/6029495/how-can-i-generate-random-number-in-specific-range-in-android/6029519#6029519
     */
    public static int randomInRange(int fromInclusive, int toExclusive) {
        return random.nextInt(toExclusive - fromInclusive) + fromInclusive;
    }

    public static String randomHexString(int length) {
        byte[] salt = new byte[length];
        new SecureRandom().nextBytes(salt);
        return hex(salt);
    }

    /**
     * return the safe index to be used in a list with size() = listSize.
     * in case using this to get the safe index for adding operation into a list 'theList', should call this function with listSize = theList.size() + 1
     * because adding operation accept index from 0 - theList.size()
     */
    public static int safeIndex(int expectedIndex, int listSize) {
        if (expectedIndex < 0) {
            return 0;
        }
        if (listSize <= 0) {
            return 0;
        }
        if (expectedIndex >= listSize) {
            return listSize - 1;
        }
        return expectedIndex;
    }

    public static String toJson(Object theObject) {
        return gsonMain.toJson(theObject);
    }

    public static String toJson(Object theObject, Type typeOfSrc) {
        return gsonMain.toJson(theObject, typeOfSrc);
    }

    public static void updateTimezoneToUtc() {
        TimeZone.setDefault(DateTimeZone.UTC.toTimeZone());
        DateTimeZone.setDefault(DateTimeZone.UTC);
    }

    public static String md5Hex(String input) {
        return hex(DigestUtils.md5(input));
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        String content = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Closeables.closeQuietly(inputStream);
        return content;
    }

    public static void inputStreamToFile(String filePath, InputStream is) throws IOException {
        inputStreamToFile(Paths.get(filePath), is);
    }

    public static void inputStreamToFile(Path path, InputStream is) throws IOException {
//        com.google.common.io.Files.createParentDirs(path.toFile());
        Files.createDirectories(path.getParent());
        Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        Closeables.closeQuietly(is);
    }

    public static String md5Hex(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return DigestUtils.md5Hex(is);
        }
    }

    public static byte[] md5(File file) throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            return DigestUtils.md5(is);
        }
    }

    /**
     * TODO: consider ordering the 2 sub-lists
     */
    public static List<LessonView> flatMapToLessons(List<EnvironmentView> groupedList) {
        return groupedList.stream()
                          .flatMap(environmentView -> {
                              Stream.Builder<LessonView> builder = Stream.<LessonView>builder();
                              environmentView.availableLessons.forEach(builder::accept);
                              environmentView.inProcessLessons.forEach(builder::accept);
                              return builder.build();
                          }).collect(toList());
    }

    public static <K_FROM, K_TO, V> Map<K_TO, V> transformMapKey(Map<K_FROM, V> fromMap, Function<Map.Entry<K_FROM, V>, K_TO> keyMapFunc) {
        return fromMap.entrySet().stream()
                      .collect(toMap(keyMapFunc,
                                     Map.Entry::getValue,
                                     (v1, v2) -> v1,
                                     TreeMap::new));
    }

    public static void deleteFileSafely(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            try {
                FileUtils.deleteQuietly(file);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    public static void createEmptyFolder(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }
    }

    public static void createFolderIfNotExist(String folder) {
        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static void deleteFileSafely(Path path) {
        try {
            FileUtils.deleteQuietly(path.toFile());
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public static boolean isLinkOk(String link) {
        try {
            int status = new JdkRequest(link)
                    .method(Request.HEAD)
                    .fetch()
                    .status();
            return status == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            log.warn("isLinkOk error. {}", e.toString());
        }

        return false;
    }

    public static boolean downloadFile(String downloadLink, Path localPath) {
        try {
            FileUtils.copyURLToFile(new URL(downloadLink), localPath.toFile());
            return true;
        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }

    public static String readFileToString(File file) {
        String filePath = "";
        try {
            if (!file.exists()) {
                log.warn("readFileToString: file not existed: {}", file.getAbsolutePath());
                return "";
            }

            filePath = file.getAbsolutePath();
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            log.error("readFileToString failed {}", filePath);
        }
        return "";
    }

    public static boolean writeStringToFile(Path target, String content) {
        try {
            return writeStringToFile(target.toFile(), content);
        } catch (Exception e) {
            log.error("", e);
        }
        return false;
    }

    public static boolean writeStringToFile(File target, String content) {
        try {
            FileUtils.deleteQuietly(target);
            FileUtils.writeStringToFile(target, content, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("", e);
        }
        return false;
    }

    public static void moveDirectory(File from, File to) {
        try {
            if (to.exists()) {
                FileUtils.deleteQuietly(to);
            }
            FileUtils.moveDirectory(from, to);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static void copyDirectory(File from, File to) {
        try {
            FileUtils.copyDirectory(from, to);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public static DateTime parseDateTimeSafely(String sDate, DateTimeFormatter... formatterList) {
        DateTime toReturn = null;
        for (DateTimeFormatter formatter : formatterList) {
            toReturn = parseDateTimeSafely(sDate, formatter);
            if (toReturn != null) {
                break;
            }
        }
        return toReturn;
    }

    public static DateTime parseDateTimeSafely(String sDate, DateTimeFormatter formatter) {
        try {
            return DateTime.parse(sDate, formatter);
        } catch (Exception e) {
            log.debug("Error while parsing date. {}", sDate);
        }
        return null;
    }

    public static long getRemoteFileSize(String theUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(theUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return 0;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static long getLocalFileSize(String localPath) {
        try {
            return getLocalFileSize(Paths.get(localPath));
        } catch (Exception ignored) { }
        return 0;
    }

    public static long getLocalFileSize(Path localPath) {
        try {
            return localPath.toFile().length();
        } catch (Exception ignored) { }
        return 0;
    }

    public static String normalizeSqlInput(String input) {
        String safeString = Strings.nullToEmpty(input);
        safeString = safeString.trim();
        safeString = StringUtils.remove(safeString, '\'');
        return safeString;
    }

    public static double convertMillisToMinutes(long millis) {
        final double minutes = millis / 1000.0d / 60.0d;
        return round(minutes, 1);
    }

    /**
     * http://stackoverflow.com/a/1361874/617232
     * <p>
     * http://www.rgagnon.com/javadetails/java-0016.html
     */
    public static double round(double d, int decimalPlace) {
        // see the Javadoc about why we use a String in the constructor
        // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    /**
     * http://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java
     */
    public static double round2(double valueToRound, int numberOfDecimalPlaces) {
        double multiplicationFactor = Math.pow(10, numberOfDecimalPlaces);
        double interestedInZeroDPs = valueToRound * multiplicationFactor;
        return Math.round(interestedInZeroDPs) / multiplicationFactor;
    }

    public static String shapeToJson(com.golftec.teaching.videoUtil.drawingToolEx.GTShape shape) {
        return gsonForGTShape.toJson(shape, com.golftec.teaching.videoUtil.drawingToolEx.GTShape.class);
    }

    public static com.golftec.teaching.videoUtil.drawingToolEx.GTShape shapeFromJson(String json) {
        return gsonForGTShape.fromJson(json, com.golftec.teaching.videoUtil.drawingToolEx.GTShape.class);
    }

    public static <C extends com.golftec.teaching.videoUtil.drawingToolEx.GTShape> C shapeFromJson(String json, Class<C> childClazz) {
        final com.golftec.teaching.videoUtil.drawingToolEx.GTShape parent = shapeFromJson(json);
        return childClazz.cast(parent);
    }

    public static <C extends com.golftec.teaching.videoUtil.drawingToolEx.GTShape> String shapeListToJson(List<C> list) {
        return gsonForGTShape.toJson(list, shapeListType);
    }

    public static List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> shapeListFromJson(String json) {
        return gsonForGTShape.fromJson(json, shapeListType);
    }

    public static <C extends com.golftec.teaching.videoUtil.drawingToolEx.GTShape> List<C> shapeListFromJson(String json, Class<C> childClazz) {
        final List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> parentList = shapeListFromJson(json);
        return parentList.stream()
                         .map(childClazz::cast)
                         .collect(toList());
    }

    public static long max(Set<Long> longs) {
        if (longs == null || longs.size() == 0) {
            return 0;
        }

        Long max = Collections.max(longs);
        if (max != null) {
            return max;
        }
        return 0;
    }

    public static void runIf(boolean shouldRun, Runnable runnable) {
        if (!shouldRun) {
            return;
        }
        runnable.run();
    }
}
