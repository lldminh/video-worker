package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.data.CoachNote;
import com.golftec.teaching.model.data.Drill;

import java.util.List;

public class LessonFinalPackage {

    private List<TelestrationVideo> telestrationVideos = null;
    private CoachNote coachNote = null;
    private List<Drill> drills = null;
    private String lessonId = "";
    private String coachId = "";

    public void setCoachNote(CoachNote coachNote) {
        this.coachNote = coachNote;
    }

    public CoachNote getCoachNote() {
        return this.coachNote;
    }

    public void setDrills(List<Drill> drills) {
        this.drills = drills;
    }

    public List<Drill> getDrills() {
        return this.drills;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonId() {
        return this.lessonId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getCoachId() {
        return this.coachId;
    }
}
