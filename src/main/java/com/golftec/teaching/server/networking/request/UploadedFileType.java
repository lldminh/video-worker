package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by hoang on 2015-08-28.
 */
public enum UploadedFileType {
    Swing("swing"),
    Telestrator("telestrator"),
    Drawing("drawing");

    private static final Map<String, UploadedFileType> itemMap = Maps.newHashMap();

    static {
        for (UploadedFileType item : UploadedFileType.values()) {
            itemMap.put(item.text, item);
        }
    }

    public final String text;

    UploadedFileType(String text) {
        this.text = text;
    }

    public static UploadedFileType from(String text) {
        return itemMap.getOrDefault(text, Swing);
    }

    @Override
    public String toString() {
        return text;
    }
}
