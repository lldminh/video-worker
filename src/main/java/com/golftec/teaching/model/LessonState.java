package com.golftec.teaching.model;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * TODO: should remove deprecated item
 * <p>
 * Created by hoang on 2015-07-20.
 */
public enum LessonState {
    NotSet(-1),

    @Deprecated
    FreeForAll(1),
    Available(1),

    @Deprecated
    Started(2),
    InProcess(2),

    @Deprecated
    Finished(3),
    SubmittedToTECswing(3),

    @Deprecated
    Processed(4),
    SubmittedToGolftec(4),

    ShownInPPC(5),

    /**
     * Should be called `DONE`
     */
    @Deprecated
    Archived(6),
    DoneAndArchived(6),

    /**
     * Old or Not used yet
     */
    Reserved(7),

    CompleteLocal(8),

    /**
     * user click cancel button on IPad.
     * The lessons with this state will still be returned and IPad will hide them if needed.
     */
    Canceled(9),;

    private static final Map<Integer, LessonState> itemMap = Maps.newHashMap();

    static {
        for (LessonState item : values()) {
            itemMap.put(item.intValue, item);
        }
    }

    public final int intValue;

    LessonState(int intValue) {
        this.intValue = intValue;
    }

    public static LessonState from(int intValue) {
        return itemMap.getOrDefault(intValue, NotSet);
    }

    public static LessonState from(String text) {
        try {
            return LessonState.valueOf(text);
        } catch (Exception ignored) { }
        return LessonState.NotSet;
    }
}
