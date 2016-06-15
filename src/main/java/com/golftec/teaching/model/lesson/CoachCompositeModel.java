package com.golftec.teaching.model.lesson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CoachCompositeModel {

    private Map<String, Coach> coachMap;

    public CoachCompositeModel() {
        coachMap = new ConcurrentHashMap<String, Coach>();
    }

    public synchronized void setCoach(Coach coach) {
        if (coachMap.containsKey(coach.id)) {
            coachMap.put(coach.id, coach);
        }
    }

    public synchronized Coach getCoach(String coachId) {
        return coachMap.get(coachId);
    }

}
