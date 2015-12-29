package com.golftec.video.production.networking.handler;

import com.golftec.teaching.common.GTUtil;
import com.golftec.video.production.common.GTResponseCode;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.model.ProcessingTelestration;
import com.golftec.video.production.model.RequestData;
import com.golftec.video.production.model.ResponseData;
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

    public ResponseData handle(Request req, spark.Response res) {

        Optional<RequestData> opt = MPJsonUtils.fromJson(req.body(), RequestData.class);
        if (!opt.isPresent()) {
            return new ResponseData(GTResponseCode.NotOk.id, "Request data is empty.");
        }
        RequestData requestData = opt.get();
        String lessonId = requestData.lessonId;
        String telestrationId = requestData.telestrationId;
        if (Strings.isNullOrEmpty(lessonId) || Strings.isNullOrEmpty(telestrationId)) {
            return new ResponseData(GTResponseCode.NotOk.id, "TelestrationVideo is empty.");
        }
        if (ProcessingTelestration.instance().contains(telestrationId)) {
            return new ResponseData(GTResponseCode.VideoIsProcessing.id, "The processing is not finished. Please wait util the process done.");
        }

        ProcessingTelestration.instance().add(telestrationId);
        GTUtil.async(() -> VideoService.composeTelestrationVideo(lessonId, telestrationId));

        return new ResponseData(GTResponseCode.Ok.id, "TelestrationVideo is processing.");
    }

}
