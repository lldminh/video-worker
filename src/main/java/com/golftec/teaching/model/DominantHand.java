package com.golftec.teaching.model;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Hoang on 2015-04-16.
 */
public enum DominantHand implements Serializable {
    Right("R", "right"),
    Left("L", "left");

    private static final Map<String, DominantHand> itemMap = Maps.newHashMap();

    static {
        for (DominantHand item : values()) {
            itemMap.put(StringUtils.lowerCase(item.text), item);
            itemMap.put(StringUtils.lowerCase(item.dbValue), item);
        }
    }

    /**
     * the value that is supposed to be inserted into database
     */
    public final String dbValue;
    private final String text;

    DominantHand(String text, String dbValue) {
        this.text = text;
        this.dbValue = dbValue;
    }

    public static DominantHand from(String text) {
        return itemMap.getOrDefault(StringUtils.lowerCase(text), Right);
    }
}
