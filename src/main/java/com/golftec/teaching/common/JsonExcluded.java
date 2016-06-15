package com.golftec.teaching.common;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Field/class/method marked with this will be excluded in json processing (both serialization and de-serialization)
 * <p>
 * https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/ExclusionStrategy.html
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({TYPE, FIELD, METHOD})
public @interface JsonExcluded {
    // nothing needed here
}
