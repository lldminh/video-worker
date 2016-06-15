package com.golftec.teaching.common;

import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.TreeMap;

/**
 * Created by hoang on 2015-06-10.
 */
public class AnnotationHistoryAdapter implements JsonDeserializer<AnnotationHistory> {

    @Override
    public AnnotationHistory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        AnnotationHistory history = GTUtil.gsonForGTShape.fromJson(json, AnnotationHistory.class);
        //NOTE: reorder hashMap, standByMap
        history.hashMap = history.hashMap != null ? new TreeMap<>(history.hashMap) : Maps.newTreeMap();
        history.standByMap = history.standByMap != null ? new TreeMap<>(history.standByMap) : Maps.newTreeMap();
        history.frameMap = history.frameMap != null ? new TreeMap<>(history.frameMap) : Maps.newTreeMap();
        return history;
    }
}
