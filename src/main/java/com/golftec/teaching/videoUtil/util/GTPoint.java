package com.golftec.teaching.videoUtil.util;

import java.io.Serializable;

/**
 * Created by sonleduc on 5/7/15.
 */
public class GTPoint implements Serializable{
    private double x = 0;
    private double y = 0;

    public GTPoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
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
}
