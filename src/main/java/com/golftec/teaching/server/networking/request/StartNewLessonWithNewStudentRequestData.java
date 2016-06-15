package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.model.lesson.Student;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-19.
 */
public class StartNewLessonWithNewStudentRequestData {

    public String coachId = "";
    public String lessonId = "";
    public String storeId = "";
    public String lessonType = "";
    public String environmentType = "";
    @SerializedName("player")
    public Student newStudent;
}
