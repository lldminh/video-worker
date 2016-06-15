package com.golftec.teaching.videoUtil.motion;

import com.golftec.teaching.model.types.MotionDataType;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;

import java.io.Serializable;

public class MotionEvent implements Serializable {

    public String method = "";
    public GTMouseEvent gtMouseEvent = null;
    public MotionDataType motionDataType = null;
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
}
