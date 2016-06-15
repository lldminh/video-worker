package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.motion.MotionViewData;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sonleduc on 5/8/15.
 */
public class MotionStandByData implements Serializable {

    public List<MotionViewData> list = Lists.newArrayList();
    private Double timestamp = 0.0;

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = (double) timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestampRounded() {
        return Math.round(timestamp);
    }
}
