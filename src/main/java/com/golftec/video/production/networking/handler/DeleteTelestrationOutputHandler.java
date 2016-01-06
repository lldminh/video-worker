package com.golftec.video.production.networking.handler;

import com.golftec.video.production.common.GTVideoProductionUtil;
import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.data.ComposeStatus;
import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.data.TelestrationStatus;
import com.golftec.video.production.model.DeleteTelestrationOutputRequestData;
import com.golftec.video.production.model.DeleteTelestrationOutputResponseData;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;

import java.util.Optional;

/**
 * Created by ThuPT on 1/6/2016.
 */
public class DeleteTelestrationOutputHandler {

    private static final Logger log = LoggerFactory.getLogger(DeleteTelestrationOutputHandler.class);

    public DeleteTelestrationOutputResponseData handle(Request req, spark.Response res) {

        Optional<DeleteTelestrationOutputRequestData> opt = MPJsonUtils.fromJson(req.body(), DeleteTelestrationOutputRequestData.class);
        if (!opt.isPresent()) {
            return new DeleteTelestrationOutputResponseData(GTResponseCode.NotOk.id, "Request data is empty.");
        }

        DeleteTelestrationOutputRequestData requestData = opt.get();
        String telestrationId = requestData.telestrationId;
        if (Strings.isNullOrEmpty(telestrationId)) {
            return new DeleteTelestrationOutputResponseData(GTResponseCode.NotOk.id, "Invalid request data.");
        }

        Integer telestrationStatus = TelestrationStatus.get().get(telestrationId);

        if (telestrationStatus == null || ComposeStatus.get(telestrationStatus) != ComposeStatus.Succeed) {
            return new DeleteTelestrationOutputResponseData(GTResponseCode.NotOk.id, "Telestration is not composed.");

        }

        if (GTVideoProductionUtil.deleteTelestrationOutputLocalFile(telestrationId)) {
            TelestrationStatus.get().remove(telestrationId);
            GTVideoProductionUtil.refreshTelestrationStatusFile();
            return new DeleteTelestrationOutputResponseData(GTResponseCode.Ok.id, "");

        } else {
            return new DeleteTelestrationOutputResponseData(GTResponseCode.NotOk.id, "Cannot delete compose output file.");
        }
    }
}
