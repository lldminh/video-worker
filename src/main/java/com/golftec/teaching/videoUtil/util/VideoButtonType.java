package com.golftec.teaching.videoUtil.util;

import java.io.Serializable;

/**
 * Created by Teo on 3/19/2015.
 */


public enum VideoButtonType implements Serializable {
    PLAY_BUTTON,
    STEP_BACK,
    STEP_FORWARD,
    SLOW_BACK,
    SUPER_SLOW_BACK,
    SLOW_FORWARD,
    SUPER_SLOW_FORWARD,
    SCRUB_CHANGE,
    SCRUB_MOUSE_DRAGGED,
    SCRUB_MOUSE_DRAG_ENTERED,
    SCRUB_MOUSE_CLICKED,
    STOP,
    PLAY,
    NOT_STARTED,
    A,
    T,
    I,
    F,
    SET_PATH,
    FLIP,
    SET_SOURCE_URL,
    START_PLAY,
    NORMAL_SPEED
}