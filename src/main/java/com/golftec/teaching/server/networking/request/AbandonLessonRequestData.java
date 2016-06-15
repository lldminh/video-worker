package com.golftec.teaching.server.networking.request;

/**
 * Created by hoang on 2015-05-27.
 */
public class AbandonLessonRequestData {

    public final String coachId;
    public final String lessonId;

    public AbandonLessonRequestData(String coachId, String lessonId) {
        this.coachId = coachId;
        this.lessonId = lessonId;
    }
}
