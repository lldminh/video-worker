package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTOval extends GTShape implements Serializable {

    protected double x = 0;
    protected double y = 0;
    protected double w = 0;
    protected double h = 0;

    protected double x1, y1 = 0;
    protected double x2, y2 = 0;

    public GTOval(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public GTOval() {
        super();
        type = "GTOval";
    }

    public GTOval(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTOval";
    }

    @Override
    public GTShape clone() {
        GTOval gtOval = new GTOval();
        gtOval.gc = gc;
        gtOval.isFinished = isFinished;
        gtOval.stroke = stroke;
        gtOval.lineWidth = lineWidth;
        gtOval.type = type;
        gtOval.highLightStroke = highLightStroke;
        gtOval.highLightlineWidth = highLightlineWidth;

        gtOval.x = x;
        gtOval.y = y;
        gtOval.w = w;
        gtOval.h = h;
        return gtOval;
    }

    public GTOval(com.golftec.teaching.videoUtil.drawingToolEx.GTOval gtOval) {
        id = gtOval.id;
        isFinished = gtOval.isFinished;
        stroke = gtOval.getColor();
        lineWidth = (float) gtOval.getLineWidth();
        type = gtOval.getType();
        highLightStroke = gtOval.getHighLightColor();
        highLightlineWidth = (float) gtOval.getHighLightlineWidth();

        x = gtOval.getX();
        y = gtOval.getY();
        w = gtOval.getW();
        h = gtOval.getH();
    }


    public GTOval(GraphicsContext gc) {
        this.gc = gc;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTOval gtOval =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTOval(this);
        return gtOval;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        gc.strokeOval(this.x, this.y, this.w, this.h);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    @Override
    public boolean isCollision(BoundingBox boundingBox) {
        return false;
    }

    @Override
    public boolean isCollision(Point2D point) {
        double orgX = this.x + this.w / 2;
        double orgY = this.y + this.h / 2;

        double a = this.w / 2;
        double b = this.h / 2;

        double shiftX = (int) point.getX() - (int) orgX;
        double shiftY = (int) point.getY() - (int) orgY;

        double val = (Math.pow(shiftX, 2) / Math.pow((int) a, 2) + Math.pow(shiftY, 2) / Math.pow((int) b, 2));

        return val <= 1.3 && val > 0.8;
    }

    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {
        this.x1 = me.getX();
        this.y1 = me.getY();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        this.x2 = me.getX();
        this.y2 = me.getY();

        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);

        this.w = Math.abs(x2 - x1);
        this.h = Math.abs(y2 - y1);
        this.draw();
    }

    @Override
    public void consumeMouseMoved(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseDragReleased(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseReleased(GTMouseEvent me) {
        this.isFinished = true;
    }
}
