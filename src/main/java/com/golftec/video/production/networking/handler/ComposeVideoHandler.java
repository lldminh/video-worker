package com.golftec.video.production.networking.handler;

import com.golftec.teaching.common.GTUtil;
import com.golftec.video.production.common.GTServerUtil;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.data.TelestrationStatus;
import com.golftec.video.production.model.ComposeVideoRequestData;
import com.golftec.video.production.model.ComposeVideoResponseData;
import com.golftec.video.production.service.VideoService;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.util.Optional;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class ComposeVideoHandler {

    private static final Logger log = LoggerFactory.getLogger(ComposeVideoHandler.class);

    public ComposeVideoResponseData handle(Request req, spark.Response res) {

        Optional<ComposeVideoRequestData> opt = MPJsonUtils.fromJson(req.body(), ComposeVideoRequestData.class);
        if (!opt.isPresent()) {
            return new ComposeVideoResponseData(GTResponseCode.NotOk.id, "Request data is empty.");
        }
        ComposeVideoRequestData requestData = opt.get();
        String lessonId = requestData.lessonId;
        String telestrationId = requestData.telestrationId;
        if (Strings.isNullOrEmpty(lessonId) || Strings.isNullOrEmpty(telestrationId)) {
            return new ComposeVideoResponseData(GTResponseCode.NotOk.id, "TelestrationVideo is empty.");
        }

        if (GTServerUtil.isTelestrationIsProcessing(telestrationId)) {
            return new ComposeVideoResponseData(GTResponseCode.VideoIsProcessing.id, "The processing is not finished. Please wait util the process done.");
        }

        if (!requestData.isForceCompose && GTServerUtil.isTelestrationComposeSucceed(telestrationId)) {
            return new ComposeVideoResponseData(GTResponseCode.VideoIsComposed.id, "The telestration is composed before.");
        }

        TelestrationStatus.instance().put(telestrationId, ComposeStatus.Processing.status);
        GTUtil.async(() -> VideoService.composeTelestrationVideo(lessonId, telestrationId));

        return new ComposeVideoResponseData(GTResponseCode.Ok.id, "TelestrationVideo is processing.");
    }

}
