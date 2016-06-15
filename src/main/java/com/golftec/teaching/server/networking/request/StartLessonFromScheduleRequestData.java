package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-22.
 */
public class StartLessonFromScheduleRequestData {

    /**
     * NOTE: this is currently be used as lessonId
     */
    public String scheduleId;

    /**
     * NOTE: this is the player's id, NOT the lesson's id
     */
    @SerializedName("wuci")
    public String wuci;

    public String lessonId;
}
