package com.golftec.teaching.model;

import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.view.LessonView;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by hoang on 2015-05-28.
 */
public class CoachLesson {

    public final String coachId;
    public final String lessonId;
    public final boolean isReserved;
    public final boolean isSelected;
    public final String id;
    public Date created = DateTime.now().toDate();

    //TODO: newly selected lesson should be on top of the in-process list
    public CoachLesson(String coachId, String lessonId, boolean isReserved, boolean isSelected) {
        this.coachId = coachId;
        this.lessonId = lessonId;
        this.isReserved = isReserved;
        this.isSelected = isSelected;
        this.id = buildId(coachId, lessonId, isReserved, isSelected);
    }

    public CoachLesson(Coach coach, LessonView lesson, boolean isReserved, boolean isSelected) {
        this.coachId = coach.id;
        this.lessonId = lesson.id;
        this.isReserved = isReserved;
        this.isSelected = isSelected;
        this.id = buildId(coachId, lessonId, isReserved, isSelected);
    }

    public static String buildId(String coachId, String lessonId, boolean isReserved, boolean isSelected) {
        return String.format("%s_%s_%s_%s", coachId, lessonId, isReserved, isSelected);
    }
}
