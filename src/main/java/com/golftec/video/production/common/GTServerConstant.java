package com.golftec.video.production.common;

import java.nio.file.Paths;

/**
 * Created by Hoang on 2015-04-16.
 */
public final class GTServerConstant {

    public static final String VERSION = "1.2.0";

    public static final String TELESTRATION_VIDEO_OUTPUT_DIR = "video-output";
    public static final String LESSON_TELESTRATIONS_DIR_NAME = "telestrations";
    public static final String LESSON_DATA_DIR = "lesson-data";
    public static final String LESSON_DATA_BACKUP_DIR = "lesson-data-backup";
    public static final String STUDENT_DATA_DIR = "student-data";
    public static final String TEC_CARD_DIR = "tec-cards";
    public static final String PRO_VIDEO_DIR = "pro-videos";
    public static final String COACH_PRO_VIDEO_PREF_DIR = "coach-pro-video-pref";
    public static final String COACH_DIR = "coach";

    public static GTServerConfig ConfigOption;
    public static String BASE_DATA_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir()).toString() : "";
    public static String BASE_LESSON_DATA_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), LESSON_DATA_DIR).toString() : "";
    public static String BASE_LESSON_DATA_BACKUP_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), LESSON_DATA_BACKUP_DIR).toString() : "";
    public static String BASE_STUDENT_DATA_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), STUDENT_DATA_DIR).toString() : "";
    public static String BASE_TEC_CARD_DATA_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), TEC_CARD_DIR).toString() : "";
    public static String BASE_PROVIDEOS_DATA_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), PRO_VIDEO_DIR).toString() : "";
    public static String BASE_COACH_PROVIDEO_PREF_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), COACH_PRO_VIDEO_PREF_DIR).toString() : "";
    public static String BASE_COACH_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir(), COACH_DIR).toString() : "";
    public static String DATA_PARENT_DIR = ConfigOption != null ? Paths.get(ConfigOption.DataDir()).toAbsolutePath().getParent().toString() : "";

}
