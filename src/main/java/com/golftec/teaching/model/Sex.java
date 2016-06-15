package com.golftec.teaching.model;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public enum Sex implements Serializable {

    Male("M", "male"),
    Female("F", "female");

    private static final Map<String, Sex> itemMap = Maps.newHashMap();

    static {
        for (Sex item : values()) {
            itemMap.put(StringUtils.lowerCase(item.text), item);
            itemMap.put(StringUtils.lowerCase(item.dbValue), item);
        }
    }

    /**
     * the value that is supposed to be insert into database.
     */
    public final String dbValue;
    private final String text;

    Sex(String text, String dbValue) {
        this.text = text;
        this.dbValue = dbValue;
    }

    public static Sex from(String text) {
        return itemMap.getOrDefault(StringUtils.lowerCase(text), Male);
    }

}
