package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-09.
 */
public class AddLessonRequestData {

    @SerializedName("wuci")
    public String wuci = "";

    public String lessonId = "";
    public String studentId = "";
    public String coachId = "";
    public String lessonType = "";
    public String environmentType = "";
}
