package com.golftec.teaching.model;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Map;

public enum SwingType implements Serializable {
    Putt("Putt"),
    Chip("Chip"),
    Pitch("Pitch"),
    Bunker("Bunker"),
    FullSwingIron("Full Swing Iron", "Iron"),
    FullSwingWood("Full Swing Wood", "Wood");

    private static final Map<String, SwingType> itemMap = Maps.newHashMap();

    static {
        for (SwingType item : values()) {
            for (String text : item.texts) {
                itemMap.put(StringUtils.lowerCase(text), item);
            }
        }
    }

    public final String text;
    private final String[] texts;

    SwingType(String... texts) {
        this.texts = texts;
        this.text = this.texts[0];
    }

    public static SwingType from(String text) {
        return itemMap.getOrDefault(StringUtils.lowerCase(text), Putt);
    }

    @Override
    public String toString() {
        return text;
    }
}
