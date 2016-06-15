package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.types.EnvironmentType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Al Wells
 */
public class EnvironmentOld implements Serializable {

    private Map<EnvironmentType, List<Lesson>> lessonMap = new ConcurrentHashMap<>();

    public EnvironmentOld() {
        List<Lesson> eventLesson = new ArrayList<>();
        lessonMap.put(EnvironmentType.GolftecEvent, eventLesson);
        List<Lesson> outdoors = new ArrayList<>();
        lessonMap.put(EnvironmentType.GolftecOutdoors, outdoors);
        List<Lesson> gtg = new ArrayList<>();
        lessonMap.put(EnvironmentType.GolftecToGo, gtg);
        List<Lesson> mptg = new ArrayList<>();
        lessonMap.put(EnvironmentType.MyProToGo, mptg);
    }

    /**
     * Thread safe method to set new lessons.
     *
     * @param lesson
     */
    public synchronized void setLesson(Lesson lesson) {
        List<Lesson> lessons = getLessonList(lesson.getEnvironmentType());
        lessons.add(lesson);
    }

    /**
     * Returns the lessons in a List<Lesson> for any Environment.
     *
     * @param environmentType
     * @return
     */
    public List<Lesson> getLessons(EnvironmentType environmentType) {
        return getLessonList(environmentType);
    }

    /**
     * Removes a Lesson from the Environment so that end users will no longer
     * see that lesson in the list of available lessons.
     *
     * @param lesson
     */
    public synchronized void removeLesson(Lesson lesson) {
        List<Lesson> lessons = getLessonList(lesson.getEnvironmentType());
        Lesson lessonToRemove = null;
        for (Lesson l : lessons) {
            if (l.getWorkflowId().equalsIgnoreCase(lesson.getWorkflowId())) {
                lessonToRemove = lesson;
                break;
            }
        }
        if (lessonToRemove != null) {
            lessons.remove(lessonToRemove);
        }
    }

    /**
     * If lesson values change, such as a coach adds a script value, pass that lesson
     * to this method and this method will update the model to replace the old lesson
     * with this lesson which will be reflected in the UI's.
     *
     * @param lesson
     */
    public synchronized void updateLesson(Lesson lesson) {
        List<Lesson> lessons = getLessonList(lesson.getEnvironmentType());
        for (Lesson l : lessons) {
            if (l.getWorkflowId().equals(lesson.getWorkflowId())) {
                //noinspection UnusedAssignment
                l = lesson;
                break;
            }
        }
    }

    /**
     * Worker method
     *
     * @param environmentType
     * @return
     */
    private synchronized List<Lesson> getLessonList(EnvironmentType environmentType) {
        List<Lesson> lessons = null;
        if (environmentType.equals(EnvironmentType.GolftecEvent)) {
            lessons = lessonMap.get(EnvironmentType.GolftecEvent);
        } else if (environmentType.equals(EnvironmentType.GolftecOutdoors)) {
            lessons = lessonMap.get(EnvironmentType.GolftecOutdoors);
        } else if (environmentType.equals(EnvironmentType.GolftecToGo)) {
            lessons = lessonMap.get(EnvironmentType.GolftecToGo);
        } else if (environmentType.equals(EnvironmentType.MyProToGo)) {
            lessons = lessonMap.get(EnvironmentType.MyProToGo);
        }
        return lessons;
    }
}
