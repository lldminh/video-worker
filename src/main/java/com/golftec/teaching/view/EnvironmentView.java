package com.golftec.teaching.view;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by hoang on 5/22/15.
 */
public class EnvironmentView {

    public String environmentId = "";
    public String environmentName = "";
    public List<LessonView> availableLessons = Lists.newArrayList();
    public List<LessonView> inProcessLessons = Lists.newArrayList();

    public EnvironmentView(String environmentId, String environmentName, List<LessonView> availableLessons, List<LessonView> inProcessLessons) {
        this.environmentId = environmentId;
        this.environmentName = environmentName;
        if (availableLessons != null) {
            this.availableLessons = availableLessons;
        }
        if (inProcessLessons != null) {
            this.inProcessLessons = inProcessLessons;
        }
    }

    public EnvironmentView() { }
}
