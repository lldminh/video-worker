package com.golftec.teaching.videoUtil.control;

import javafx.util.Duration;

/**
 * Created by sonleduc on 3/23/15.
 */
public interface IVideoEvent {
    public void frameChanged(int frame);
    public void started();
    public void timeChanged(Duration time);
    public void positionChanged(double position);
}
