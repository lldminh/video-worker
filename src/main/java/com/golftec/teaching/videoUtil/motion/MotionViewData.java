package com.golftec.teaching.videoUtil.motion;

import java.io.Serializable;
import com.golftec.teaching.model.types.MotionDataType;

/**
 * Created by sonleduc on 5/8/15.
 */
public class MotionViewData implements Serializable{

    MotionDataType motionDataType = MotionDataType.HB;
    private double x = 0;
    private double y = 0;

    public double getX() {
        return this.x;
    }

    public void setX(double x){
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y){
        this.y = y;
    }

    public MotionDataType getMotionDataType(){
        return this.motionDataType;
    }

    public void setMotionDataType(MotionDataType motionDataType) {
        this.motionDataType = motionDataType;
    }

}
