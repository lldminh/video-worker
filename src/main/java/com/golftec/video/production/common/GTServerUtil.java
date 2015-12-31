package com.golftec.video.production.common;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.server.networking.response.Response;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static com.golftec.teaching.common.GTResponseCode.NotOk;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class GTServerUtil {

    private static final Logger log = LoggerFactory.getLogger(GTServerUtil.class);


    public static void initAfterOptionParsed() {
        GTServerConstant.BASE_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir()).toString();
        GTServerConstant.BASE_LESSON_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.LESSON_DATA_DIR).toString();
        GTServerConstant.BASE_LESSON_DATA_BACKUP_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.LESSON_DATA_BACKUP_DIR).toString();
        GTServerConstant.BASE_STUDENT_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.STUDENT_DATA_DIR).toString();
        GTServerConstant.BASE_TEC_CARD_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.TEC_CARD_DIR).toString();
        GTServerConstant.BASE_PROVIDEOS_DATA_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.PRO_VIDEO_DIR).toString();
        GTServerConstant.BASE_COACH_PROVIDEO_PREF_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.COACH_PRO_VIDEO_PREF_DIR).toString();
        GTServerConstant.BASE_COACH_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir(), GTServerConstant.COACH_DIR).toString();
        GTServerConstant.DATA_PARENT_DIR = Paths.get(GTServerConstant.ConfigOption.DataDir()).toAbsolutePath().getParent().toString();
    }

    public static Response initResponse(Request request) {
        if (request != null) {
            return new Response(NotOk.id, "", null, request.methodId, request.requestId);
        }
        return new Response();
    }

    public static void downloadTelestrationVideoJsonFile(String lessonId, String telestrationId) {
        try {
            String relativeURL = GTServerConstant.LESSON_DATA_DIR + "/" + lessonId + "/" + GTServerConstant.LESSON_TELESTRATIONS_DIR_NAME + "/" + telestrationId + "/" + telestrationId + ".json";
            URL url = new URL(GTServerConstant.ConfigOption.LessonDataFileHost(), relativeURL);
            final Path jsonFilePath = constructTelestrationJsonFilePath(lessonId, telestrationId);
            org.apache.commons.io.FileUtils.copyURLToFile(url, jsonFilePath.toFile());
            log.info("downloadTelestrationVideoJsonFile succeed.");
        } catch (MalformedURLException e) {
            log.error("downloadTelestrationVideoJsonFile", e);
        } catch (IOException e) {
            log.error("downloadTelestrationVideoJsonFile", e);
        }
    }

    public static Optional<TelestrationVideo> loadTelestrationFromJsonFile(String lessonId, String telestrationId) {
        try {
            final Path jsonFilePath = constructTelestrationJsonFilePath(lessonId, telestrationId);
            final String json = FileUtils.readFileToString(jsonFilePath.toFile(), StandardCharsets.UTF_8);
            final TelestrationVideo telestrationVideo = GTUtil.fromJson(json, TelestrationVideo.class);
            return Optional.ofNullable(telestrationVideo);
        } catch (Exception e) {
            log.warn("Error while loadTelestrationFromJsonFile {}/{}", lessonId, telestrationId);
        }
        return Optional.empty();
    }

    public static Path constructTelestrationJsonFilePath(String lessonId, String telestrationId) {
        return Paths.get(GTServerConstant.BASE_LESSON_DATA_DIR, lessonId, GTServerConstant.LESSON_TELESTRATIONS_DIR_NAME, telestrationId, telestrationId + ".json");
    }

    public static Path constructTelestrationMetaDataFilePath(String lessonId, String telestrationId) {
        return Paths.get(GTServerConstant.BASE_LESSON_DATA_DIR, lessonId, GTServerConstant.LESSON_TELESTRATIONS_DIR_NAME, telestrationId, "telestration.json");
    }

    public static Path constructTelestrationFinalVideoFilePath(String lessonId, String telestrationId) {
        return Paths.get(GTServerConstant.BASE_LESSON_DATA_DIR, lessonId, GTServerConstant.LESSON_TELESTRATIONS_DIR_NAME, telestrationId, telestrationId + ".mp4");
    }

}
