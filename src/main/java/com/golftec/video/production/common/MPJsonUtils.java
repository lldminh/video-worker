package com.golftec.video.production.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 * Created by hoang on 2015-10-13.
 */
public final class MPJsonUtils {

    public static final Gson gson = new GsonBuilder().create();

    public static <T> String toJson(T src) {
        return gson.toJson(src);
    }

    public static <T> Optional<T> fromJson(String json, Class<T> clazz) {
        return fromJson(json, (Type) clazz);
    }

    public static <T> Optional<T> fromJson(String json, Type clazz) {
        try {
            T obj = gson.fromJson(json, clazz);
            return Optional.ofNullable(obj);
        } catch (Exception ignored) {
        }

        // in case of exception
        return Optional.empty();
    }
}
