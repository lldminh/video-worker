package com.golftec.video.production.networking.handler;

import com.golftec.teaching.common.GTUtil;
import com.golftec.video.production.common.GTVideoProductionUtil;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.data.ServerStatus;
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

        if(ServerStatus.isBusy()) {
            return new ComposeVideoResponseData(GTResponseCode.ServerBusy.id, "The other telestration is being composed. Try it later.");
        }

        Optional<ComposeVideoRequestData> opt = MPJsonUtils.fromJson(req.body(), ComposeVideoRequestData.class);
        if (!opt.isPresent()) {
            return new ComposeVideoResponseData(GTResponseCode.NotOk.id, "Request data is empty.");
        }
        ComposeVideoRequestData requestData = opt.get();
        String telestrationJsonFileUrl = requestData.telestrationJsonFileUrl;
        String telestrationWavFileUrl = requestData.telestrationWavFileUrl;
        String telestrationId = requestData.telestrationId;
        if (Strings.isNullOrEmpty(telestrationJsonFileUrl) || Strings.isNullOrEmpty(telestrationId) || Strings.isNullOrEmpty(telestrationWavFileUrl)) {
            return new ComposeVideoResponseData(GTResponseCode.NotOk.id, "Invalid request data.");
        }


        //forcing: restart compose
        if (!requestData.isForceCompose && GTVideoProductionUtil.isTelestrationIsProcessing(telestrationId)) {
            return new ComposeVideoResponseData(GTResponseCode.TelestrationIsProcessing.id, "The processing is not finished. Please wait util the process done.");
        }

        //forcing: restart compose
        if (!requestData.isForceCompose && GTVideoProductionUtil.isTelestrationComposeSucceed(telestrationId)) {
            return new ComposeVideoResponseData(GTResponseCode.TelestrationIsComposed.id, "The telestration is composed before.");
        }

        //set server to busy
        ServerStatus.setBusy();
        GTVideoProductionUtil.updateTelestrationStatus(telestrationId, ComposeStatus.Processing.status);
        GTUtil.async(() -> VideoService.composeTelestrationVideo(telestrationId, telestrationJsonFileUrl, telestrationWavFileUrl));

        return new ComposeVideoResponseData(GTResponseCode.Ok.id, "The telestration is processing.");
    }

}
