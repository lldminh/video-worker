package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.types.MotionDataType;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * This model for storing motion data associated with a swing. You need
 * a Swing for MotionData.
 *
 * @author Al Wells
 */
public class MotionData implements Serializable {

    private int frame;
    private int value;
    private MotionDataType type;
    private Color color;

    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public MotionDataType getType() {
        return type;
    }

    public void setType(MotionDataType type) {
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(String color) {
        if (color.equals("red")) {
            this.color = Color.RED;
        }
        if (color.equals("yellow")) {
            this.color = Color.YELLOW;
        }
        if (color.equals("green")) {
            this.color = Color.GREEN;
        }
    }
}
