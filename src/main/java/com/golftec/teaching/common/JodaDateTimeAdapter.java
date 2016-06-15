package com.golftec.teaching.common;

import com.google.common.base.Strings;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Type;

/**
 * https://sites.google.com/site/gson/gson-type-adapters-for-common-classes
 * <p>
 * Created by hoang on 2015-07-09.
 */
public class JodaDateTimeAdapter implements JsonSerializer<DateTime>, JsonDeserializer<DateTime> {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern(GTConstants.MAIN_JSON_DATETIME_FORMAT);

    // No need for an InstanceCreator since DateTime provides a no-args constructor
    @Override
    public JsonElement serialize(DateTime src, Type srcType, JsonSerializationContext context) {
        return new JsonPrimitive(src == null ? StringUtils.EMPTY : formatter.print(src));
    }

    @Override
    public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        final String dateAsString = json.getAsString();
        return Strings.isNullOrEmpty(dateAsString) ? null : formatter.parseDateTime(dateAsString);
    }
}
