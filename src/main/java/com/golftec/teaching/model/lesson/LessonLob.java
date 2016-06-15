package com.golftec.teaching.model.lesson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-09-10.
 */
public class LessonLob {

    @SerializedName("displayName")
    public String displayName = "";

    @SerializedName("instructor")
    public String instructor = "";

    @SerializedName("date")
    public String date = "";

    public LessonLob(String displayName, String instructor, String date) {
        this.displayName = displayName;
        this.instructor = instructor;
        this.date = date;
    }
}
