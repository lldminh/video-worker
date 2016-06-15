package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.server.networking.type.TransmissionType;
import com.golftec.teaching.server.networking.util.ByteObjectCommon;

/**
 * Old type, will be replace with new Response soon
 */
@Deprecated
public abstract class Response2 implements IResponse {

    private TransmissionType responseType;
    private int responseSize;
    private Object response;

    private Response2() {
    }

    protected Response2(TransmissionType responseType, Object responseObject) {
        this.responseType = responseType;
        this.response = responseObject;
        this.responseSize = ByteObjectCommon.getObjectSize(this);
    }

    public TransmissionType getResponseType() {
        return responseType;
    }

    public Object getResponse() {
        return response;
    }

    public int getResponseSize() {
        return responseSize;
    }
}
