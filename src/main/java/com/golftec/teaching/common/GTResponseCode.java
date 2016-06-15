package com.golftec.teaching.common;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by hoang on 5/21/15.
 */
public enum GTResponseCode {
    UNKNOWN(-1),
    Ok(0),
    NotOk(1),
    AuthFail(404),
    AddSwingFailed_SwingExisted(501),
    AddDrawingFailed_DrawingExisted(502),;

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
