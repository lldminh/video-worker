package com.golftec.teaching.videoUtil.motion;


import com.golftec.teaching.model.types.MotionDataType;
import javafx.scene.paint.Color;

public class MotionData {

    public MotionData(int frame, Color color, MotionDataType motionDataType, int value) {
        this.frame = frame;
        this.color = color;
        this.motionDataType = motionDataType;
        this.value = value;
//        this.image = Common.getMotionImage(this);
    }

    public MotionData() {
    }

    private MotionDataType motionDataType = null;
    private Integer status = null;
    private int frame = 0;
    private int value = 0;
    private Color color = null;

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColort(Color color) {
        this.color = color;
    }

    public MotionDataType getMotionDataType() {
        return this.motionDataType;
    }

    public void setMotionDataType(MotionDataType motionDataType) {
        this.motionDataType = motionDataType;
    }

    public int getFrame() {
        return this.frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }
//    public Image image = null;
}
