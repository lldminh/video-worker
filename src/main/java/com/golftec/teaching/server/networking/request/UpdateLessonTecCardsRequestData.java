package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 2015-06-26.
 */
public class UpdateLessonTecCardsRequestData {

    public String lessonId = "";

    @SerializedName("tecCardIds")
    public List<String> tecCardIds = Lists.newArrayList();
}
