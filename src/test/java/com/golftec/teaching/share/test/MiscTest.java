package com.golftec.teaching.share.test;

import com.golftec.teaching.common.GTUtil;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by hoang on 2015-06-25.
 */
public class MiscTest {

    @Test
    public void test_path() {
        {
            Path path = Paths.get("temp", "sub-folder");
            assertEquals("temp/sub-folder", path.toString());
        }

        {
            Path path = Paths.get("temp", "sub-folder", "/");
            assertEquals("temp/sub-folder", path.toString());
        }

        {
            Path path = Paths.get("temp", "sub-folder/");
            assertEquals("temp/sub-folder", path.toString());
        }
    }

    @Test
    public void test_md5() {
        String plain = "BALL_FLIGHT_Basics 2015";
        final String hash = GTUtil.md5Hex(plain);

        // match with http://www.md5.cz/
        assertEquals("818e81c7518dadb79c92e7f13771798d", hash);
    }

    @Test
    public void test_getFileSize() {
        final int expected = 3620504;

        String remotePath = "http://golftec_cloud.s3.amazonaws.com/ppc_drills/address/drill_320240.mp4";
        final long remoteFileSize = GTUtil.getRemoteFileSize(remotePath);
        assertEquals(expected, remoteFileSize);

        Path localPath = Paths.get("/Users/hoang/Projects/GolfTec/server-svn/golftec-teaching-server/temp/test-data", "drill_320240.mp4");
        assertEquals(expected, localPath.toFile().length());
    }

    @Test
    public void test_convert_to_minutes() {
        int tele01_millis = 26000;
        int tele02_millis = 181000;

        double minutes = GTUtil.convertMillisToMinutes(tele01_millis + tele02_millis);
        System.out.println(minutes);
        assertEquals(3.5, minutes, 0.0000001);
    }

    @Test
    public void misc_datetime_test() {
        long dateLong = 1446638434703l;
        final DateTime dateTime = new DateTime(dateLong);
        System.out.println(dateTime.toDateTimeISO());
    }

    @Test
    public void misc_md5() {
        String plain = "http://s3.amazonaws.com/golftec_cloud/pro_videos/johnson_s_d/video.mp4";
        final String s = GTUtil.md5Hex(plain);
        System.out.println(s);
        assertEquals("85e56c86fc87d7109d22af48ca323e6b", s);
    }

    @Test
    public void misc_checksum() throws IOException {
        String link = "http://s3.amazonaws.com/golftec_cloud/pro_videos/johnson_s_d/video.mp4";
        final File temp = File.createTempFile("video_temp", ".mp4");
        final Path path = temp.toPath();
        System.out.println(path.toAbsolutePath().toString());
        final boolean downloadOK = GTUtil.downloadFile(link, path);
        assertTrue(downloadOK);

        {
            final Checksum checksum = FileUtils.checksum(path.toFile(), new CRC32());
            final HashCode hash = Files.hash(path.toFile(), Hashing.crc32());
            System.out.println("checksum: " + checksum.getValue());
            System.out.println("hash: " + hash.padToLong());
            assertEquals(checksum.getValue(), hash.padToLong());
        }

        {
            System.out.println("MD5:");
            final HashCode hash = Files.hash(path.toFile(), Hashing.md5());
            final String hex = GTUtil.hex(hash.asBytes());
            System.out.println(hash);
            System.out.println(hex);
            assertEquals("7efc812cca20e98e164e2c05df5d74ab", hex);
            assertEquals("7efc812cca20e98e164e2c05df5d74ab", hash.toString());
            assertEquals(hex, hash.toString());
        }
    }

    /**
     * Summary: just use GTUtil.md5Hex(path.toFile());
     * It is the shortest way. Tested that it is equal to other methods
     */
    @Test
    public void misc_checksum_2() throws IOException {
        String link = "http://mirrors.viethosting.vn/apache/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip";
        String checksumLink = "http://www.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.zip.md5";
        final File temp = File.createTempFile("maven-3.3.9_", ".zip");
        final Path path = temp.toPath();
        System.out.println(path.toAbsolutePath().toString());
        final boolean downloadOK = GTUtil.downloadFile(link, path);
        assertTrue(downloadOK);

        {
            final HashCode hash = Files.hash(path.toFile(), Hashing.md5());
            final String hex = GTUtil.hex(hash.asBytes());
            final String md5Hex = GTUtil.md5Hex(path.toFile());
            final String checksum = new JdkRequest(checksumLink)
                    .method(Request.GET)
                    .fetch()
                    .body();
            System.out.println(hex);
            System.out.println(hash);
            System.out.println(md5Hex);
            System.out.println(checksum);
            assertEquals("e7ebd0b8d6811b42a5dad91fb27fe9b4", checksum);
            assertEquals("e7ebd0b8d6811b42a5dad91fb27fe9b4", hex);
            assertEquals("e7ebd0b8d6811b42a5dad91fb27fe9b4", md5Hex);
            assertEquals("e7ebd0b8d6811b42a5dad91fb27fe9b4", hash.toString());
        }
    }
}
