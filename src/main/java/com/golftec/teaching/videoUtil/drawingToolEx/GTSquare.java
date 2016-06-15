package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;

public class GTSquare extends GTRectangle implements Serializable{

    public GTSquare(double x, double y, double width) {
        super(x, y, width, width);
    }

    public GTSquare() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTSquare";
    }

    public GTSquare(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTSquare";
    }

    public GTSquare(com.golftec.teaching.videoUtil.drawingTool.GTSquare gtSquare) {
        id = gtSquare.id;
        isFinished = gtSquare.isFinished;
        color = gtSquare.getStroke();
        lineWidth = (float) gtSquare.getLineWidth();
        type = gtSquare.getType();
        highLightColor = gtSquare.getHighLightStroke();
        highLightlineWidth = (float) gtSquare.getHighLightlineWidth();

        x = gtSquare.getX();
        y = gtSquare.getY();
        w = gtSquare.getW();
        h = gtSquare.getH();
    }

    public GTSquare(Graphics2D gc) {
        this.gc = gc;
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTSquare(this);
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        double x2 = me.getX();
        double y2 = me.getY();
        double x1 = this.x;
        double y1 = this.y;

        this.w = this.h = Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));

        draw();
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }
}
