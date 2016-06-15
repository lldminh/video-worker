package com.golftec.teaching.model;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public enum LessonType implements Serializable {
    Short30("Short30", "Lesson30"),
    Short60("Short60", "Lesson60"),
    Playing("Playing"),;

    private static final Map<String, LessonType> itemMap = Maps.newHashMap();

    static {
        for (LessonType item : values()) {
            for (String name : item.names) {
                itemMap.put(StringUtils.lowerCase(name), item);
            }
        }
    }

    public String name;
    public String[] names;

    LessonType(String... names) {
        this.names = names;
        this.name = this.names[0];
    }

    public static LessonType from(String text) {
        return itemMap.getOrDefault(StringUtils.lowerCase(text), Playing);
    }
}
