package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.server.networking.type.TransmissionType;

public class EndpointResponse extends Response2 {

    public EndpointResponse(TransmissionType responseType,
                            Object responseObject) {
        super(responseType, responseObject);
    }
}
