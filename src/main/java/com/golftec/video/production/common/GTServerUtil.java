package com.golftec.video.production.common;

import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.server.networking.response.Response;

import java.nio.file.Paths;

import static com.golftec.teaching.common.GTResponseCode.NotOk;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class GTServerUtil {

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
}
