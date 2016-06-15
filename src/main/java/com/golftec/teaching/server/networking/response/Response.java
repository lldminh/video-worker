package com.golftec.teaching.server.networking.response;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;

public class Response implements Serializable {

    public Object data = null;
    public int methodId = 0;
    public String requestId = "";
    public int responseCode = 0;
    public String errorMessage = "";
    public Date responseDate = DateTime.now().toDate();

    public Response(int responseCode, String errorMessage, Object data, int methodId, String requestId) {
        this.responseCode = responseCode;
        this.errorMessage = errorMessage;
        this.data = data;
        this.methodId = methodId;
        this.requestId = requestId;
    }

    public Response() { }
}
