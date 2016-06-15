package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-03.
 */
public class AddTelestrationToLessonRequestData {

    public String lessonId;

    @SerializedName("telestrationVideo")
    public TelestrationVideo telestrationData;

    public AddTelestrationToLessonRequestData(String lessonId, TelestrationVideo telestrationData) {
        this.lessonId = lessonId;
        this.telestrationData = telestrationData;
    }
}
