package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;

public class GTOval extends GTShape implements Serializable {

    protected Double x = null;
    protected Double y = null;
    protected Double w = null;
    protected Double h = null;

    public GTOval(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public GTOval() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTOval";
    }

    public GTOval(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTOval";
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getW() {
        return w;
    }

    public Double getH() {
        return h;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTOval(this);
    }

    public GTOval(com.golftec.teaching.videoUtil.drawingTool.GTOval gtOval) {
        id = gtOval.id;
        isFinished = gtOval.isFinished;
        color = gtOval.getStroke();
        lineWidth = (float) gtOval.getLineWidth();
        type = gtOval.getType();
        highLightColor = gtOval.getHighLightStroke();
        highLightlineWidth = (float) gtOval.getHighLightlineWidth();

        x = gtOval.getX();
        y = gtOval.getY();
        w = gtOval.getW();
        h = gtOval.getH();
    }

    public GTOval(Graphics2D gc) {
        this.gc = gc;
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public void drawIt(Graphics2D gc) {
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        gc.drawOval(this.x.intValue(), this.y.intValue(), this.w.intValue(), this.h.intValue());
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
        this.x = me.getX();
        this.y = me.getY();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        double x1 = this.x;
        double y1 = this.y;
        double x2 = me.getX();
        double y2 = me.getY();
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
