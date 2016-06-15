package com.golftec.teaching.videoUtil.util;

import java.io.Serializable;

/**
 * Created by Teo on 3/19/2015.
 */
public class VideoBoardEvent implements Serializable {

    public VideoButtonType videoButtonType = null;
    public float value = 0;
    public String filePath = "";
    public String sourceURL = "";
    private Double timestamp = 0.0;

    public VideoBoardEvent(VideoButtonType videoButtonType) {
        this.videoButtonType = videoButtonType;
    }

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
