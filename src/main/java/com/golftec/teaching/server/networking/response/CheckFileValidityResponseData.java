package com.golftec.teaching.server.networking.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-08-28.
 */
public class CheckFileValidityResponseData {

    @SerializedName("isValid")
    private final boolean isValid;

    public CheckFileValidityResponseData(boolean isValid) {
        this.isValid = isValid;
    }
}
