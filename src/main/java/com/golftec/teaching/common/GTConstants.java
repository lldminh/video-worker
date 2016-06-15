package com.golftec.teaching.common;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;

/**
 * Created by Hoang on 2015-04-16.
 */
public final class GTConstants {

    public static final String[] DATE_TIME_FORMAT_LIST = new String[]{"yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss"};
    public static final String PACKAGE_OF_SHAPE_CLASSES = "com.golftec.teaching.videoUtil.drawingTool.";

    public static final String BASE_VIDEO_FACTORY_DIR = "temp" + File.separator + "video-factory" + File.separator;

    public static final DateTimeFormatter DATE_FORMAT_LONG = DateTimeFormat.forPattern("E MMM d, yyyy");

    public static final String MAIN_JSON_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final DateTimeFormatter MAIN_JSON_DATETIME_FORMATTER = DateTimeFormat.forPattern(MAIN_JSON_DATETIME_FORMAT);
}
