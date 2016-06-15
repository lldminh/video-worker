package com.golftec.teaching.model;

import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.model.lesson.Lesson;
import com.golftec.teaching.model.lesson.Student;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 * Created by hoang on 2015-06-08.
 */
public class ScheduleItem {

    public String id;

    public Student student;
    public Lesson lesson;
    public Coach coach;
    public GolfCenter center;

    public LocalTime plannedLessonTime;
    public DateTime plannedLessonDateTime;

    public DateTime expectedPlanEnd;
    public DateTime actualPlanEnd;

    public ScheduleItem() { }
}
