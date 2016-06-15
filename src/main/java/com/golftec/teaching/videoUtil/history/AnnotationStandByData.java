package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sonleduc on 5/6/15.
 */
public class AnnotationStandByData implements Serializable {

    private List<GTShape> gtShapeList = Lists.newArrayList();
    private List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> gtShapeExList = Lists.newArrayList();
    private long desFrame = 0;
    private Double timestamp = 0.0;

    public void setDesFrame(long frame) {
        this.desFrame = frame;
    }

    public long getDesFrame() {
        return this.desFrame;
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

    public void setGtShapeList(List<GTShape> gtShapeList) {
        this.gtShapeList = gtShapeList;
        if (this.gtShapeList != null) {
            for (int i = 0; i < gtShapeList.size(); i++) {
                try {
                    GTShape gtShape = gtShapeList.get(i);
                    if (gtShape != null) {
                        com.golftec.teaching.videoUtil.drawingToolEx.GTShape gtShapeEx =
                                gtShape.getGTShapeEx();
                        gtShapeExList.add(gtShapeEx);
                    }
                } catch (Exception ex) {
                    System.out.println("setGtShapeList: " + ex);
                }
            }
        }
    }

    public void setGtShapeExList(List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> gtShapeExList) {
        this.gtShapeExList = gtShapeExList;
    }

    public List<GTShape> getGtShapeList() {
        return this.gtShapeList;
    }

    public List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> getGtShapeExList() {
        return this.gtShapeExList;
    }
}
