package com.golftec.video.production.networking.handler;

import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.video.production.common.GTResponseCode;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.model.ProcessingTelestration;
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

        Optional<TelestrationVideo> opt = MPJsonUtils.fromJson(req.body(), TelestrationVideo.class);
        if (!opt.isPresent()) {
            return new ResponseData(GTResponseCode.NotOk.id, "Request data is empty.");
        }
        TelestrationVideo telestrationVideo = opt.get();
        String telestrationId = telestrationVideo.getTelestrationId();
        if (Strings.isNullOrEmpty(telestrationId)) {
            return new ResponseData(GTResponseCode.NotOk.id, "TelestrationVideo is empty.");
        }
        if (ProcessingTelestration.instance().contains(telestrationId)) {
            return new ResponseData(GTResponseCode.VideoIsProcessing.id, "The TelestrationVideo is processing.");
        }

        asyncServiceMethod(telestrationVideo);

        return new ResponseData(GTResponseCode.Ok.id, "TelestrationVideo is processing.");
    }

    public void asyncServiceMethod(TelestrationVideo telestrationVideo) {
        Runnable task = new Runnable() {

            @Override
            public void run() {
                try {
                    VideoService.composeTelestrationVideo(telestrationVideo);
                } catch (Exception ex) {
                    log.error("ComposeVideoHandler {}", ex);
                }
            }
        };
        new Thread(task, "ComposeVideo is starting").start();
    }
}
