package com.golftec.teaching.model.lesson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-09-10.
 */
public class LessonLeft {

    @SerializedName("displayName")
    public String displayName = "";

    @SerializedName("lessonCount")
    public String lessonCount = "";

    public LessonLeft(String displayName, String lessonCount) {
        this.displayName = displayName;
        this.lessonCount = lessonCount;
    }
}
