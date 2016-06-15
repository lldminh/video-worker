package com.golftec.teaching.server.networking.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Minh on 6/10/2016.
 */
public class CheckUpdateVersionResponseData {

    @SerializedName("isOutdated")
    public boolean isOutdated = false;

    @SerializedName("newestVersion")
    public String newestVersion = "";

    @SerializedName("updateUrl")
    public String updateUrl = "";
}
