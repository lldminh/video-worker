package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.server.networking.type.TransmissionType;

import java.io.Serializable;

public interface IResponse extends Serializable {

    public TransmissionType getResponseType();

    public Object getResponse();

    public int getResponseSize();

}
