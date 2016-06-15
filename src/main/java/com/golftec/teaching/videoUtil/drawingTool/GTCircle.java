package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTCircle extends GTOval implements Serializable{
    public GTCircle(double x, double y, double radius) {
        super(x, y, radius * 2d, radius * 2d);
    }

    public GTCircle() {
        super();
        type = "GTCircle";
    }

    public GTCircle(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTCircle";
    }

    public GTCircle(com.golftec.teaching.videoUtil.drawingToolEx.GTCircle gtCircle) {
        id = gtCircle.id;
        isFinished = gtCircle.isFinished;
        stroke = gtCircle.getColor();
        lineWidth = (float) gtCircle.getLineWidth();
        type = gtCircle.getType();
        highLightStroke = gtCircle.getHighLightColor();
        highLightlineWidth = (float) gtCircle.getHighLightlineWidth();

        x = gtCircle.getX();
        y = gtCircle.getY();
        w = gtCircle.getW();
        h = gtCircle.getH();
    }

    public GTCircle(GraphicsContext gc) {
        this.gc = gc;
    }


    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        this.x2 = me.getX();
        this.y2 = me.getY();

        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);

        this.w = this.h = Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));
        this.draw();
    }

    @Override
    public GTShape clone() {
        GTCircle gtCircle = new GTCircle();
        gtCircle.gc = gc;
        gtCircle.isFinished = isFinished;
        gtCircle.stroke = stroke;
        gtCircle.lineWidth = lineWidth;
        gtCircle.type = type;
        gtCircle.highLightStroke = highLightStroke;
        gtCircle.highLightlineWidth = highLightlineWidth;

        gtCircle.x = x;
        gtCircle.y = y;
        gtCircle.w = w;
        gtCircle.h = h;
        return gtCircle;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTCircle gtCircle =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTCircle(this);
        return gtCircle;
    }

    @Override
    public boolean isCollision(Point2D point) {

        double orgX = this.x + this.w / 2;
        double orgY = this.y + this.w / 2;

        double radius = this.w / 2;

        double val = (Math.pow((int) point.getX() - (int) orgX, 2) + Math.pow((int) point.getY() - (int) orgY, 2) - Math.pow((int) radius, 2));

        return val <= 300 && val >= -300;
    }
}
