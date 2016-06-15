package com.golftec.teaching.videoUtil.util;

import javafx.scene.input.MouseEvent;

import java.io.Serializable;

/**
 * Created by Teo on 3/19/2015.
 */
public class GTMouseEvent implements Serializable {
    private double x = 0;
    private double y =0;

    public GTMouseEvent(MouseEvent e){
        this.x = e.getX();
        this.y = e.getY();
    }

    public GTMouseEvent(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }
}
