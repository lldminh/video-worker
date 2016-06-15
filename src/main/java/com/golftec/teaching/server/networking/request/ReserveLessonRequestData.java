package com.golftec.teaching.server.networking.request;

/**
 * Created by hoang on 2015-05-25.
 */
public class ReserveLessonRequestData {

    public String coachId;
    public String lessonId;

    public ReserveLessonRequestData(String coachId, String lessonId) {
        this.coachId = coachId;
        this.lessonId = lessonId;
    }
}
