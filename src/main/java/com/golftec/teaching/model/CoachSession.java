package com.golftec.teaching.model;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by hoang on 2015-05-31.
 */
public class CoachSession {

    public String coachId;
    public String token;
    public CoachSessionStatus status;
    public String id;
    public Date created = DateTime.now().toDate();

    public CoachSession(String coachId, String token, CoachSessionStatus status) {
        this.coachId = coachId;
        this.token = token;
        this.status = status;
        this.id = buildId(coachId, token);
    }

    public static String buildId(String coachId, String token) {return String.format("%s_%s", coachId, token);}
}
