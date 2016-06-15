package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2016-06-10.
 */
public class CheckUpdateVersionRequestData {

    @SerializedName("clientVersion")
    public String clientVersion = "";
}
