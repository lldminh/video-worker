package com.golftec.teaching.view;

import com.golftec.teaching.model.types.EnvironmentType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by hoang on 2015-06-17.
 */
public class LessonByCoachView {

    @SerializedName("inProcess")
    public List<LessonView> inProcessLessons = Lists.newArrayList();

    @SerializedName("available")
    public Map<EnvironmentType, List<LessonView>> availableGroupedByEnv = Maps.newHashMap();
}
