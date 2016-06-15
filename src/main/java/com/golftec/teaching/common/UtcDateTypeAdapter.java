package com.golftec.teaching.common;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Because of this issue, I need to use a custom adapter class to make sure it use expected timezone (UTC in this case)
 * https://code.google.com/p/google-gson/issues/detail?id=281
 * <p>
 * Created by Hoang.
 * Created on 2014-09-09.
 */
public class UtcDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final DateFormat dateFormat;

    public UtcDateTypeAdapter() {
        dateFormat = new SimpleDateFormat(GTConstants.MAIN_JSON_DATETIME_FORMAT);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public synchronized Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
            return dateFormat.parse(jsonElement.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }

    @Override
    public synchronized JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(dateFormat.format(date));
    }
}
