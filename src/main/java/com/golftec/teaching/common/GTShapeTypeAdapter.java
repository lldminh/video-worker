package com.golftec.teaching.common;

import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * http://ovaraksin.blogspot.com/2011/05/json-with-gson-and-abstract-classes.html
 * <p>
 * Created by hoang on 2015-06-03.
 */
public class GTShapeTypeAdapter implements JsonSerializer<GTShape>, JsonDeserializer<GTShape> {

    @Override
    public GTShape deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");
        try {
            return context.deserialize(element, Class.forName(GTConstants.PACKAGE_OF_SHAPE_CLASSES + type));
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown element type: " + type, e);
        }
    }

    @Override
    public JsonElement serialize(GTShape src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
