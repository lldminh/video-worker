package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hoang on 2016-02-23.
 */
public class DrillDeviceTimestamp {

    @SerializedName("drillID")
    public String drillId = "";

    @SerializedName("timestamp")
    public long timestamp;

    @SerializedName("timestampMetaData")
    public String timestampMetadata = "";
}
