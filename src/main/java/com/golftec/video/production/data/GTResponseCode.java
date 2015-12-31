package com.golftec.video.production.data;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by ThuPT on 12/28/2015.
 */
public enum GTResponseCode {
    UNKNOWN(-1),
    Ok(0),
    NotOk(1),
    VideoIsProcessing(2),
    VideoIsComposed(3),;

    private static final Map<Integer, GTResponseCode> map = Maps.newHashMap();

    static {
        for (GTResponseCode method : GTResponseCode.values()) {
            map.put(method.id, method);
        }
    }

    public int id;

    GTResponseCode(int id) {
        this.id = id;
    }

    public static GTResponseCode getById(int id) {
        return map.getOrDefault(id, UNKNOWN);
    }
}

