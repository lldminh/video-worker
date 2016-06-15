package com.golftec.teaching.model;

import org.joda.time.DateTime;

import java.util.Date;

public class SwitchState {

    public LessonState previousState;
    public LessonState presentState;
    public Date dateStateChange = DateTime.now().toDate();
}
