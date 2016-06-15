package com.golftec.teaching.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Field/class/method marked with this will be excluded in json serialization (no json string will be generated)
 * <p>
 * https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/ExclusionStrategy.html
 * <p>
 * Created by Hoang on 2014-07-09.
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD})
public @interface JsonSerializationExcluded {
    // nothing needed here
}
