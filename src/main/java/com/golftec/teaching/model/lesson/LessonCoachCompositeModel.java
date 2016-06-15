package com.golftec.teaching.model.lesson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LessonCoachCompositeModel {

    /**
     * If you need to get a list of all lessons a coach owns, you can do so here.
     * Convenient if you want to build an environments model for instance for any
     * given coach.
     */
    private Map<String, List<Lesson>> coachToLesson;
    /**
     * If you need to get a list of all coaches that any given lesson
     * can be seen by, can get from this list. Good for quickly
     * telling all coaches when some lesson status changes.
     */
    private Map<String, List<Coach>> lessonToCoach;

    public LessonCoachCompositeModel() {
        coachToLesson = new ConcurrentHashMap<String, List<Lesson>>();
        lessonToCoach = new ConcurrentHashMap<String, List<Coach>>();
    }

    /**
     * Must call this method with a lesson and a coach any time you see a new coach (s/he logged in)
     * and that coach has lessons. Otherwise, this composite model won't know about either.
     *
     * @param lesson
     * @param coach
     */
    public void addLessonAndCoach(Lesson lesson, Coach coach) {
        addCoachToLesson(coach, lesson);
        addLessonToCoach(lesson, coach);
    }

    /**
     * If you added a coach and a lesson, you can call this
     * method to get all lessons that any given coach has access to.
     *
     * @param coach
     * @return
     */
    public List<Lesson> getCoachLessons(Coach coach) {
        return coachToLesson.get(coach.id);
    }

    /**
     * If you added a lesson and a coach, you can call this method to
     * get a list of all coaches a lesson is assigned to.
     *
     * @param lesson
     * @return
     */
    public List<Coach> getLessonCoaches(Lesson lesson) {
        return lessonToCoach.get(lesson.getWorkflowId());
    }

    /**
     * Call when a coach logs out to remove the coach from the model.
     *
     * @param coach
     */
    public void removeCoach(Coach coach) {
        List<Lesson> lessons = coachToLesson.remove(coach.id);
        List<Lesson> lessonsToRemove = new ArrayList<Lesson>();
        for (Lesson l : lessons) {
            List<Coach> coaches = getLessonCoaches(l);
            int coachIndex = 0;
            for (int i = 0; i < coaches.size(); i++) {
                if (coaches.get(i).id.equals(coach.id)) {
                    coachIndex = i;
                    break;
                }
            }
            coaches.remove(coachIndex);
            if (coaches.size() == 0) {
                lessonsToRemove.add(l);
            }
        }
        for (Lesson l : lessonsToRemove) {
            removeLesson(l);
        }
    }

    /**
     * Call when a lesson is completed and should be removed from the model or
     * when no more coaches are using the lesson.
     *
     * @param lesson
     */
    public void removeLesson(Lesson lesson) {
        List<Coach> coaches = lessonToCoach.remove(lesson.getWorkflowId());
        List<Coach> coachToRemove = new ArrayList<Coach>();
        for (Coach c : coaches) {
            List<Lesson> coachLessons = getCoachLessons(c);
            if (coachLessons.size() == 1) {
                coachToRemove.add(c);
            }
        }
        for (Coach c : coachToRemove) {
            removeCoach(c);
        }
    }

    //private methods that fill the model.
    private void addCoachToLesson(Coach coach, Lesson lesson) {
        //noinspection UnusedAssignment
        List<Lesson> lessons = null;
        if (coachToLesson.containsKey(coach.id)) {
            lessons = coachToLesson.get(coach.id);
        } else {
            lessons = new ArrayList<Lesson>();
            coachToLesson.put(coach.id, lessons);
        }
        lessons.add(lesson);
    }

    private void addLessonToCoach(Lesson lesson, Coach coach) {
        //noinspection UnusedAssignment
        List<Coach> coaches = null;
        if (lessonToCoach.containsKey(lesson.getWorkflowId())) {
            coaches = lessonToCoach.get(lesson.getWorkflowId());
        } else {
            coaches = new ArrayList<Coach>();
            lessonToCoach.put(lesson.getWorkflowId(), coaches);
        }
        coaches.add(coach);
    }
}
