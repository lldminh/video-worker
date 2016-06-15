package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.model.lesson.EnvironmentOld;
import com.golftec.teaching.server.networking.type.GTMethod;

@Deprecated()
public class EnvironmentResponse extends Response {

    public EnvironmentResponse(EnvironmentOld environmentOld, String requestId, int responseCode, String errorMessage) {
        super(responseCode, errorMessage, environmentOld, GTMethod.UNKNOWN.id, requestId); //TODO: method
    }
}
