package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-07-27.
 */
public class SearchStudentRequestData {

    @SerializedName("name")
    public String studentName;

    @SerializedName("email")
    public String studentEmail;

    @SerializedName("wuci")
    public String wuci;

    @SerializedName("wuciOrEmail")
    public String wuciOrEmail;

    @SerializedName("isMyPlayer")
    public boolean isMyPlayer;

    @SerializedName("isMyCenter")
    public boolean isMyCenter;
}
