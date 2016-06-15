package com.golftec.teaching.view;

import com.golftec.teaching.model.lesson.MotionData;
import com.golftec.teaching.model.types.MotionDataType;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hoang on 5/22/15.
 */
public class MotionDataView2 implements Serializable{

    @SerializedName("frameNumber")
    public int frame = 0;
    public int value = 0;
    public MotionDataType type = MotionDataType.SHS;
    @SerializedName("color")
    public String hexColor = "";

    public MotionDataView2(MotionData motionData) {
        if (motionData != null) {
            frame = motionData.getFrame();
            value = motionData.getValue();
            type = motionData.getType();
            if (motionData.getColor() != null) {
                hexColor = motionData.getColor().toString(); // already become r-g-b-a string
            }
        }
    }

    public MotionDataView2() { }
}
