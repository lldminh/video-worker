package com.golftec.video.production.common;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.TelestrationStatus;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class GTVideoProductionUtil {

    private static final Logger log = LoggerFactory.getLogger(GTVideoProductionUtil.class);


    public static void initAfterOptionParsed() {
        GTServerConstant.BASE_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir()).toString();
        GTServerConstant.BASE_TELESTRATION_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TELESTRATIONS_DIR_NAME).toString();
        GTServerConstant.BASE_LESSON_DATA_BACKUP_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.LESSON_DATA_BACKUP_DIR).toString();
        GTServerConstant.BASE_STUDENT_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.STUDENT_DATA_DIR).toString();
        GTServerConstant.BASE_TEC_CARD_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TEC_CARD_DIR).toString();
        GTServerConstant.BASE_PROVIDEOS_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.PRO_VIDEO_DIR).toString();
        GTServerConstant.BASE_COACH_PROVIDEO_PREF_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.COACH_PRO_VIDEO_PREF_DIR).toString();
        GTServerConstant.BASE_COACH_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.COACH_DIR).toString();
        GTServerConstant.DATA_PARENT_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir()).toAbsolutePath().getParent().toString();
        GTServerConstant.BASE_TELESTRATION_STATUS_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TELESTRATION_STATUS_DIR).toString();
        GTServerConstant.SERVER_PUBLIC_IP = queryPublicIp();
    }

    public static void initTelestrationStatus() {
        try {
            final Path jsonFilePath = constructTeletestrationStatusFilePath();
            log.info("initTelestrationStatus file path: {}", jsonFilePath.toString());
            File file = jsonFilePath.toFile();
            if (!file.exists()) {
                log.info("initTelestrationStatus file does not exist, create file path: {}", jsonFilePath.toString());
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            final String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
            Type type = new TypeToken<Map<String, Integer>>() {
            }.getType();
            Map<String, Integer> statusMap = GTUtil.fromJson(json, type);
            if (statusMap != null) {
                TelestrationStatus.get().putAll(statusMap);
            }
            clearComposingTelestrationAfterInit();
        } catch (IOException e) {
            log.warn("Error while initTelestrationStatus", e);
        } catch (Exception e) {
            log.warn("Error while initTelestrationStatus", e);
        }
    }

    public static void clearAllWavAndJsonFileNotUse() {

        File telestrationFiles = Paths.get(GTServerConstant.BASE_TELESTRATION_DATA_DIR).toFile();
        log.info("clearAllWavAndJsonFileNotUse: start delete all files .wav and .json from : {}", telestrationFiles.getAbsolutePath());
        if (!telestrationFiles.exists()) {
            // no telestration data for this lesson
            return;
        }
        Files.fileTreeTraverser()
             .children(telestrationFiles)
             .forEach(telestrationDir -> {

                 if (!telestrationDir.isDirectory()) {
                     // don't care about file here
                     return;
                 }

                 File telestrationMp4File = Paths.get(telestrationDir.getAbsolutePath(), telestrationDir.getName() + ".mp4").toFile();
                 if (!telestrationMp4File.exists()) {
                     return;
                 }
                 try {
                     Path telestrationMetaFile = Paths.get(telestrationDir.getAbsolutePath(), telestrationDir.getName() + ".json");
                     if (telestrationMetaFile.toFile().exists()) {
                         GTUtil.deleteFileSafely(telestrationMetaFile);
                     }
                     Path telestrationWavFile = Paths.get(telestrationDir.getAbsolutePath(), telestrationDir.getName() + ".wav");
                     if (telestrationWavFile.toFile().exists()) {
                         GTUtil.deleteFileSafely(telestrationWavFile);
                     }
                 } catch (Exception e) {
                     log.error("Error when delete .wav and .json of telestration {}", telestrationDir.getName());
                 }
             });
    }

    private static void clearComposingTelestrationAfterInit() {
        for (String telestrationId : TelestrationStatus.get().keySet()) {
            if (ComposeStatus.get(TelestrationStatus.get().get(telestrationId)) == ComposeStatus.Composing) {
                boolean isDeleted = deleteTelestrationOutputLocalFile(telestrationId);
                if (isDeleted) {
                    log.info("clearComposingTelestrationAfterInit: composing telestration {} is deleted.", telestrationId);
                    TelestrationStatus.get().remove(telestrationId);
                } else {
                    log.error("clearComposingTelestrationAfterInit: Cannot delete composing telestration {}", telestrationId);
                }
            }
        }
        refreshTelestrationStatusFile();
    }


    public static void downloadTelestrationJsonFile(String telestrationJsonFileUrl, String telestrationId) {
        try {
            URL url = new URL(telestrationJsonFileUrl);
            final Path jsonFilePath = constructTelestrationJsonFilePath(telestrationId);
            org.apache.commons.io.FileUtils.copyURLToFile(url, jsonFilePath.toFile());
            log.info("downloadTelestrationJsonFile to {} succeed.", jsonFilePath);
        } catch (MalformedURLException e) {
            log.error("downloadTelestrationJsonFile", e);
        } catch (IOException e) {
            log.error("downloadTelestrationJsonFile", e);
        }
    }

    public static void downloadTelestrationWavFile(String telestrationWavFileUrl, String telestrationId) {
        try {
            URL url = new URL(telestrationWavFileUrl);
            final Path jsonFilePath = constructTelestrationWavFilePath(telestrationId);
            org.apache.commons.io.FileUtils.copyURLToFile(url, jsonFilePath.toFile());
            log.info("downloadTelestrationWavFile to {} succeed.", jsonFilePath);
        } catch (MalformedURLException e) {
            log.error("downloadTelestrationWavFile", e);
        } catch (IOException e) {
            log.error("downloadTelestrationWavFile", e);
        }
    }

    public static Optional<TelestrationVideo> loadTelestrationFromJsonFile(String telestrationId) {
        try {
            final Path jsonFilePath = constructTelestrationJsonFilePath(telestrationId);
            final String json = FileUtils.readFileToString(jsonFilePath.toFile(), StandardCharsets.UTF_8);
            final TelestrationVideo telestrationVideo = GTUtil.fromJson(json, TelestrationVideo.class);
            return Optional.ofNullable(telestrationVideo);
        } catch (Exception e) {
            log.warn("Error while loadTelestrationFromJsonFile {}", telestrationId);
        }
        return Optional.empty();
    }

    public static Path constructTelestrationJsonFilePath(String telestrationId) {
        return Paths.get(GTServerConstant.BASE_TELESTRATION_DATA_DIR, telestrationId, telestrationId + ".json");
    }

    public static Path constructTelestrationWavFilePath(String telestrationId) {
        return Paths.get(GTServerConstant.BASE_TELESTRATION_DATA_DIR, telestrationId, telestrationId + ".wav");
    }

    public static Path constructTelestrationFinalVideoFilePath(String telestrationId) {
        return Paths.get(GTServerConstant.BASE_TELESTRATION_DATA_DIR, telestrationId, telestrationId + ".mp4");
    }

    public static Path constructTeletestrationStatusFilePath() {
        return Paths.get(GTServerConstant.BASE_TELESTRATION_STATUS_DIR, "telestration-status.json");
    }

    public static Path constructTeletestrationFilePath(String telestrationId) {
        return Paths.get(GTServerConstant.BASE_TELESTRATION_DATA_DIR, telestrationId);
    }

    public static boolean isTelestrationIsProcessing(String telestrationId) {
        boolean isProcess = false;
        Integer status = TelestrationStatus.get().get(telestrationId);
        if (status != null && ComposeStatus.Composing.status.equals(status)) {
            isProcess = true;
        }
        return isProcess;
    }

    public static boolean isTelestrationComposeSucceed(String telestrationId) {
        boolean isProcess = false;
        Integer status = TelestrationStatus.get().get(telestrationId);
        if (status != null && ComposeStatus.Succeed.status.equals(status)) {
            isProcess = true;
        }
        return isProcess;
    }

    public static Path constructTelestrationOutputFilePath(String telestrationId) {
        return Paths.get(GTServerConstant.TELESTRATIONS_DIR_NAME, telestrationId, GTServerConstant.TELESTRATION_VIDEO_OUTPUT_DIR, GTServerConstant.TELESTRATION_OUT_FILE);
    }

    public static String constructDownloadLink(String path) throws URISyntaxException {

        StringBuilder url = (new StringBuilder()).append("http://")
                .append(GTServerConstant.SERVER_PUBLIC_IP)
                                                 .append(":")
                                                 .append(GTServerConstant.ConfigOption.workerFileServerPort())
                                                 .append("/")
                                                 .append(StringUtils.replace(path, "\\", "/"));
        return url.toString();
    }


    public static void updateTelestrationStatus(String telestrationId, Integer status) {
        try {
            TelestrationStatus.get().put(telestrationId, status);
            final Path jsonFilePath = constructTeletestrationStatusFilePath();
            File file = jsonFilePath.toFile();
            String content = GTUtil.toJson(TelestrationStatus.get());
            FileUtils.deleteQuietly(file);
            FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("updateTelestrationStatus", e);
        }
    }

    public static void refreshTelestrationStatusFile() {
        try {
            final Path jsonFilePath = constructTeletestrationStatusFilePath();
            File file = jsonFilePath.toFile();
            String content = GTUtil.toJson(TelestrationStatus.get());
            FileUtils.deleteQuietly(file);
            FileUtils.writeStringToFile(file, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error("updateTelestrationStatus", e);
        }
    }

    public static boolean deleteTelestrationOutputLocalFile(String telestrationId) {
        try {
            Path telestrationFilePath = constructTeletestrationFilePath(telestrationId);
            File file = telestrationFilePath.toFile();
            FileUtils.deleteDirectory(file);
            if (file.exists()) {
                return false;
            } else {
                return true;
            }
        } catch (IOException e) {
            log.error("deleteTelestrationOutputLocalFile", e);
        }
        return false;
    }

    public static String queryPublicIp() {
        try {
            return new JdkRequest("http://checkip.amazonaws.com")
                    .method(Request.GET)
                    .fetch()
                    .body()
                    .trim();
        } catch (IOException e) {
            log.error("queryPublicIp", e);
        }
        return new String();
    }

}
