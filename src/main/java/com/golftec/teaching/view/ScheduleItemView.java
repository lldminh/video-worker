package com.golftec.teaching.view;

import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.model.lesson.Student;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

/**
 * Created by hoang on 2015-06-08.
 */
public class ScheduleItemView {

    public String coachId;

    public String lessonId;

    @SerializedName("lessonDateTime")
    public DateTime scheduleDateTime;

    @SerializedName("lessonTime")
    public String scheduleTime;

    @Deprecated
    @SerializedName("fullName")
    public String studentName;

    @Deprecated
    @SerializedName("studentEmail")
    public String studentEmail;

    @Deprecated
    @SerializedName("studentPhone")
    public String studentPhone;

    /**
     * NOTE: currently will map with lesson.id, as wuci turns out to be player's id
     */
    @SerializedName("scheduleId")
    public String id;

    /**
     * NOTE: consider using student.wuci
     */
    @SerializedName("wuci")
    public String wuci;

    /**
     * TODO: this is for backward compatibility only, after Tao made the changes on Ipad, we will not use this one.
     */
    @Deprecated
    @SerializedName("studentId")
    public String studentId;

    public String coachName;

    @Deprecated
    public String centerName;

    /**
     * should use data from student object
     */
    @Deprecated
    @SerializedName("lastLesson")
    public String lastLessonDate;

    @Deprecated
    public String lessonsTaken;

    @Deprecated
    public String lessonsRemaining;

    @SerializedName("student")
    public Student student;

    @Deprecated
    public String fitTaken;

    @Deprecated
    public String ppcVisit;

    @SerializedName("lessonType")
    public String lessonType;

    public ScheduleItemView(String scheduleId, DateTime scheduleDateTime, Coach coach, LessonView lesson) {
        this(scheduleId, scheduleDateTime, coach.id, coach.name, lesson);
    }

    public ScheduleItemView(String scheduleId, DateTime scheduleDateTime, String coachId, String coachName, LessonView lesson) {
        this.id = scheduleId;
        this.scheduleDateTime = scheduleDateTime;
        this.scheduleTime = scheduleDateTime.toString("hh:mm a");

        this.coachId = coachId;
        this.coachName = coachName;

        this.lessonId = lesson.id;
        this.lessonType = lesson.lessonType; //TODO: make key name in enum

        Student student = lesson.student;
        if (student != null) {
            this.studentName = String.format("%s %s", student.firstName, student.lastName);
            this.wuci = student.wuci;
            this.studentId = student.studentId; //TODO: remove this after Tao finish moving to using wuci
            this.studentEmail = Strings.nullToEmpty(student.email);
            this.studentPhone = Strings.nullToEmpty(student.phoneNumber);
            if (student.lastLessonDateTime != null) {
                this.lastLessonDate = student.lastLessonDateTime.toString("yyyy-MM-dd");
            }
            this.student = student;
        }
    }
//    public String appointmentId; // probably the same as id
//    public String clientId; // probably the same as student.studentId
//    public String storeId; // probably the same as golfCenter.id
}
