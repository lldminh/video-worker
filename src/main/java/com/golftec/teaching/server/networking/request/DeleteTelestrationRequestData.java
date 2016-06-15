package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2016-06-07.
 */
public class DeleteTelestrationRequestData {

    @SerializedName("telestrationId")
    public String telestrationId = "";

    @SerializedName("lessonId")
    public String lessonId = "";
}
