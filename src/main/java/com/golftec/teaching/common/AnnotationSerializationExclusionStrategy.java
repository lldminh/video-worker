package com.golftec.teaching.common;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/ExclusionStrategy.html
 * <p>
 * Created by Hoang on 2014-07-09.
 */
public class AnnotationSerializationExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        return f.getAnnotation(JsonSerializationExcluded.class) != null;
    }

    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return clazz.getAnnotation(JsonSerializationExcluded.class) != null;
    }
}
