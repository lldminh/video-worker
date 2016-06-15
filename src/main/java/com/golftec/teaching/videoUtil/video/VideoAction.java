package com.golftec.teaching.videoUtil.video;

/**
 * Created by sonleduc on 3/1/15.
 */
public class VideoAction {

    public enum Action {
        PLAY,
        STOP,
        RESUME,
        SLOW_REWIND,
        SUPPER_SLOW_REWIND,
        SLOW_FORWARD,
        SUPER_SLOW_FORWARD,
        STEP_BACK,
        STEP_FORWARD
    }

    public VideoAction(int millisecond, Action action){
        this.time = millisecond;
        this.action = action;
    }

    private int time = 0; // in milisecond
    private Action action;
}
