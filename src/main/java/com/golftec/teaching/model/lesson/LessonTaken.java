package com.golftec.teaching.model.lesson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-09-10.
 */
public class LessonTaken {

    @SerializedName("type")
    public String type = "";

    @SerializedName("description")
    public String description = "";

    public LessonTaken(String type, String description) {
        this.type = type;
        this.description = description;
    }
}
