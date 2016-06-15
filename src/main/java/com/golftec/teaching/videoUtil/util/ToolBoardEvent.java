package com.golftec.teaching.videoUtil.util;

import java.io.Serializable;

/**
 * Created by Teo on 3/17/2015.
 */
public class ToolBoardEvent implements Serializable {

    public ToolType toolType = null;
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
