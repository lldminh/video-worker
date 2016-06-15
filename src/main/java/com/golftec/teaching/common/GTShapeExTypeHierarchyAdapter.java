package com.golftec.teaching.common;

import com.golftec.teaching.videoUtil.drawingToolEx.GTShape;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * http://ovaraksin.blogspot.com/2011/05/json-with-gson-and-abstract-classes.html
 * <p>
 * Created by hoang on 2015-07-17.
 */
public class GTShapeExTypeHierarchyAdapter implements JsonSerializer<GTShape>, JsonDeserializer<GTShape> {

    @Override
    public GTShape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            Type theSubType = Class.forName(type);
            return GTUtil.gsonBasic.fromJson(element, theSubType);
        } catch (Exception e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }

    @Override
    public JsonElement serialize(GTShape src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        result.add("properties", GTUtil.gsonBasic.toJsonTree(src, src.getClass()));
        return result;
    }
}
