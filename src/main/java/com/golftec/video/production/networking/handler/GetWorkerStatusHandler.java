package com.golftec.video.production.networking.handler;

import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.data.ServerStatus;
import com.golftec.video.production.model.StatusWorkerResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

/**
 * Created by ThuPT on 1/4/2016.
 */
public class GetWorkerStatusHandler {

    private static final Logger log = LoggerFactory.getLogger(ComposeVideoHandler.class);

    public StatusWorkerResponseData handle(Request req, spark.Response res) {

        log.info("Get status of this video worker");
        try {

            if (ServerStatus.isBusy()) {
                return new StatusWorkerResponseData(GTResponseCode.ServerBusy.id, ServerStatus.getCurrentCompose());
            }
        } catch (Exception e) {
            log.warn("Error while initTelestrationStatus", e);
        }
        return new StatusWorkerResponseData(GTResponseCode.TelestrationIsNotComposed.id, ServerStatus.getCurrentCompose());
    }
}
