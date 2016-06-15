package com.golftec.teaching.common;

import com.golftec.teaching.videoUtil.history.MotionHistory;
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
public class MotionHistoryAdapter implements JsonDeserializer<MotionHistory> {

    @Override
    public MotionHistory deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        MotionHistory history = GTUtil.gsonBasic.fromJson(json, MotionHistory.class);
        //NOTE: reorder hashMap, standByMap
        history.hashMap = history.hashMap != null ? new TreeMap<>(history.hashMap) : Maps.newTreeMap();
        history.standByMap = history.standByMap != null ? new TreeMap<>(history.standByMap) : Maps.newTreeMap();
        return history;
    }
}
