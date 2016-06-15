package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;

public class GTCircle extends GTOval implements Serializable{
    public GTCircle(double x, double y, double radius) {
        super(x, y, radius * 2d, radius * 2d);
    }

    public GTCircle() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTCircle";
    }

    public GTCircle(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTCircle";
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTCircle(this);
    }

    public GTCircle(Graphics2D gc) {
        this.gc = gc;
    }

    public GTCircle(com.golftec.teaching.videoUtil.drawingTool.GTCircle gtCircle) {
        id = gtCircle.id;
        isFinished = gtCircle.isFinished;
        color = gtCircle.getStroke();
        lineWidth = (float) gtCircle.getLineWidth();
        type = gtCircle.getType();
        highLightColor = gtCircle.getHighLightStroke();
        highLightlineWidth = (float) gtCircle.getHighLightlineWidth();

        x = gtCircle.getX();
        y = gtCircle.getY();
        w = gtCircle.getW();
        h = gtCircle.getH();
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        double x2 = me.getX();
        double y2 = me.getY();

        double x1 = this.x;
        double y1 = this.y;

        this.w = this.h = Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));
        this.draw();
    }

    @Override
    public boolean isCollision(Point2D point) {

        double orgX = this.x + this.w / 2;
        double orgY = this.y + this.w / 2;

        double radius = this.w / 2;

        double val = (Math.pow((int) point.getX() - (int) orgX, 2) + Math.pow((int) point.getY() - (int) orgY, 2) - Math.pow((int) radius, 2));

        return val <= 300 && val >= -300;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getW(){
        return this.w;
    }

    public Double getH(){
        return this.h;
    }
}
