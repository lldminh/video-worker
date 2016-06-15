package com.golftec.teaching.server.networking.request;

import java.io.Serializable;

/**
 * Created by Hoang on 2015-05-23.
 */
public class GetLessonListRequestData implements Serializable {

    public final String coachId;

    public GetLessonListRequestData(String coachId) {
        this.coachId = coachId;
    }
}
