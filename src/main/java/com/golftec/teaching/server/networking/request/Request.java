package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.server.networking.type.GTMethod;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    @SerializedName("version")
    public String clientVersion = "";

    public String coachId = "";
    public String token = "";
    public Object data = null;
    public int methodId = 0;
    public String requestId = "";
    public String installationId = "";
    public Date requestDate = DateTime.now().toDate();
    public Date requestLocalDate = DateTime.now(DateTimeZone.getDefault()).toDate();

    public Request(int methodId, Object data, String installationId, String requestId) {
        this.methodId = methodId;
        this.data = data;
        this.installationId = installationId;
        this.requestId = requestId;
    }

    public Request(GTMethod method, Object data, String installationId, String requestId) {
        this(method.id, data, installationId, requestId);
    }
}
