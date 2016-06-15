package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.server.networking.type.TransmissionType;

import java.io.Serializable;

public interface IRequest extends Serializable {

    public String getRequestToken();

    public Object getRequest();

    public TransmissionType getRequestType();

    public int getRequestSize();

}
