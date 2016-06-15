package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.model.lesson.Lesson;
import com.golftec.teaching.server.networking.type.GTMethod;

@Deprecated()
public class LessonResponse extends Response {

    protected LessonResponse(Lesson lessonObject, String requestId, int responseCode, String errorMessage) {
        super(responseCode, errorMessage, lessonObject, GTMethod.UNKNOWN.id, requestId); //TODO: method
    }
}
