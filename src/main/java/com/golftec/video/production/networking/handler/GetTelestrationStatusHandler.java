package com.golftec.video.production.networking.handler;

import com.golftec.video.production.common.GTServerUtil;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.data.TelestrationStatus;
import com.golftec.video.production.model.GetTelestrationStatusRequestData;
import com.golftec.video.production.model.GetTelestrationStatusResponseData;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by ThuPT on 1/4/2016.
 */
public class GetTelestrationStatusHandler {

    private static final Logger log = LoggerFactory.getLogger(ComposeVideoHandler.class);

    public GetTelestrationStatusResponseData handle(Request req, spark.Response res) {


        try {
            Optional<GetTelestrationStatusRequestData> opt = MPJsonUtils.fromJson(req.body(), GetTelestrationStatusRequestData.class);
            if (!opt.isPresent()) {
                return new GetTelestrationStatusResponseData(GTResponseCode.NotOk.id, "Request data is empty.", "");
            }

            GetTelestrationStatusRequestData requestData = opt.get();

            String telestrationId = requestData.telestrationId;
            if (Strings.isNullOrEmpty(telestrationId)) {
                return new GetTelestrationStatusResponseData(GTResponseCode.NotOk.id, "TelestrationId is empty.", "");

            }
            return getTelestrationStatus(telestrationId);
        } catch (URISyntaxException e) {
            log.error("GetTelestrationStatusHandler", e);
            return new GetTelestrationStatusResponseData(GTResponseCode.NotOk.id, "URISyntax error.", "");
        }
    }

    private GetTelestrationStatusResponseData getTelestrationStatus(String telestrationId) throws URISyntaxException {

        Integer status = TelestrationStatus.get().get(telestrationId);

        if (status == null) {
            return new GetTelestrationStatusResponseData(GTResponseCode.TelestrationIsNotProcessed.id, "TelestrationId is not processed.", "");
        }

        switch (ComposeStatus.get(status)) {
            case Succeed:
                Path path = GTServerUtil.constructTelestrationOutputFilePath(telestrationId);
                String url = GTServerUtil.constructDownloadLink(path.toString());
                return new GetTelestrationStatusResponseData(GTResponseCode.TelestrationIsNotProcessed.id, "TelestrationId is composed successfully.", url);
            case Fail:
                return new GetTelestrationStatusResponseData(GTResponseCode.TelestrationIsNotProcessed.id, "TelestrationId is composed fail.", "");
            case Processing:
                return new GetTelestrationStatusResponseData(GTResponseCode.TelestrationIsNotProcessed.id, "TelestrationId is processing.", "");
        }

        return null;
    }

}
