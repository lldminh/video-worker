package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 2015-06-16.
 */
public class UpdateLessonDrillsRequestData {

    public String lessonId = "";

    @SerializedName("drillIds")
    public List<String> drillIds = Lists.newArrayList();
}
