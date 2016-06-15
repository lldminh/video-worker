package com.golftec.teaching.model.lesson;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LessonReservedModel extends LessonCoachCompositeModel {

    private Map<String, Long> lessonReserveTimeMap;

    public LessonReservedModel() {
        super();
        lessonReserveTimeMap = new ConcurrentHashMap<String, Long>();
    }

    public void addLessonAndCoach(Lesson lesson, Coach coach) {
        super.addLessonAndCoach(lesson, coach);
        Long time = System.currentTimeMillis();
        lessonReserveTimeMap.put(lesson.getWorkflowId(), time);
    }

    public void removeCoach(Coach coach) {
        List<Lesson> lessons = getCoachLessons(coach);
        for (Lesson l : lessons) {
            lessonReserveTimeMap.remove(l.getWorkflowId());
        }
        super.removeCoach(coach);
    }

    public void removeLesson(Lesson lesson) {
        lessonReserveTimeMap.remove(lesson.getWorkflowId());
        super.removeLesson(lesson);
    }

}
