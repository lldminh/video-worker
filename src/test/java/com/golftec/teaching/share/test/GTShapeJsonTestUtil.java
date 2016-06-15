package com.golftec.teaching.share.test;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.videoUtil.drawingTool.GTShape;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by hoang on 2015-07-17.
 */
public final class GTShapeJsonTestUtil {

    public static final Type shapeListType = new com.google.common.reflect.TypeToken<ArrayList<GTShape>>() {}.getType();

    public static String shapeToJson(GTShape shape) {
        return GTUtil.gsonForGTShape.toJson(shape, GTShape.class);
    }

    public static GTShape shapeFromJson(String json) {
        return GTUtil.gsonForGTShape.fromJson(json, GTShape.class);
    }

    public static <C extends GTShape> C shapeFromJson(String json, Class<C> childClazz) {
        final GTShape parent = shapeFromJson(json);
        return childClazz.cast(parent);
    }

    public static <C extends GTShape> String shapeListToJson(List<C> list) {
        return GTUtil.gsonForGTShape.toJson(list, shapeListType);
    }

    public static List<GTShape> shapeListFromJson(String json) {
        return GTUtil.gsonForGTShape.fromJson(json, shapeListType);
    }

    public static <C extends GTShape> List<C> shapeListFromJson(String json, Class<C> childClazz) {
        final List<GTShape> parentList = shapeListFromJson(json);
        return parentList.stream().map(childClazz::cast).collect(toList());
    }
}
