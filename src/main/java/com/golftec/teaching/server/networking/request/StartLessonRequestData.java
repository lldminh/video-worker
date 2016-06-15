package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-08.
 */
public class StartLessonRequestData {

    /**
     * TODO: this is NOT enough, wuci is player's id, we need lesson's id
     */
    @SerializedName("wuci")
    public String wuci;

    public String lessonId;
}
