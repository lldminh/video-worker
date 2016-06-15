package com.golftec.teaching.videoUtil.control;

/**
 * Created by sonleduc on 5/9/15.
 */
public enum VideoStatus {
    NOT_STARTED,
    IS_PLAYING,
    IS_SLOWING_BACK,
    IS_SUPER_SLOW_BACK,
    IS_SUPER_SLOW_FORWARD,
    IS_SLOWING_FORWARD,
    IS_ENDSONG,
    IS_PAUSING,
    IS_FINISH // send this event when finish recording
}