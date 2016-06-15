package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 2015-06-23.
 */
public class FinishLessonRequestData {

    public String lessonId = "";
    public String storeId = "";
    /**
     * TODO: use this to send to lesson_submit.php
     */
    public String lessonType = "";
    @SerializedName("finishedTelestrations")
    public List<FinishedTelestration> finishedTelestrations = Lists.newArrayList();

    @SerializedName("finishedDrawings")
    public List<String> finishedDrawings = Lists.newArrayList();
}
