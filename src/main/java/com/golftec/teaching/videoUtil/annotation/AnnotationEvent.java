package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.MouseEventType;

import java.io.Serializable;

/**
 * Created by sonleduc on 3/20/15.
 */
public class AnnotationEvent implements Serializable {

    public GTMouseEvent mouseEvent = null;
    public MouseEventType mouseEventType = null;
    private Double timestamp = 0.0;

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = (double) timestamp;
    }

    public long getTimestampRounded() {
        return Math.round(timestamp);
    }

    public void print() {
        System.out.println("TimeStamp: " + timestamp + ", Type: " + mouseEventType + " X: " + mouseEvent.getX() + ", Y: " + mouseEvent.getY());
    }
}
