package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.control.VideoStatus;

import java.io.Serializable;

/**
 * Created by sonleduc on 5/8/15.
 */
public class VideoData implements Serializable {

    private VideoStatus status = VideoStatus.NOT_STARTED;

    private long frame = 0;
    private String sourceURL = "";
    private String filePath = "";
    private Double timestamp = 0.0;

    public Double getTimestamp() {
        return timestamp;
    }

    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    public String getFilePath(){
        return this.filePath;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public void setTimestamp(long time) {
        this.timestamp = (double) time;
    }

    public long getTimestampRounded() {
        return Math.round(timestamp);
    }

    public VideoStatus getStatus() {
        return this.status;
    }

    public void setStatus(VideoStatus status) {
        this.status = status;
    }

    public long getFrame() {
        return this.frame;
    }

    public void setFrame(long frame) {
        this.frame = frame;
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }
}
