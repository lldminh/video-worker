package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;

public class GTRectangle extends GTShape implements Serializable {

    protected Double x = null;
    protected Double y = null;
    protected Double w = null;
    protected Double h = null;

    public GTRectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle";
    }

    public GTRectangle(GTColor color) {
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle";
    }

    public GTRectangle(com.golftec.teaching.videoUtil.drawingTool.GTRectangle gtRectangle) {
        id = gtRectangle.id;
        isFinished = gtRectangle.isFinished;
        color = gtRectangle.getStroke();
        lineWidth = (float) gtRectangle.getLineWidth();
        type = gtRectangle.getType();
        highLightColor = gtRectangle.getHighLightStroke();
        highLightlineWidth = (float) gtRectangle.getHighLightlineWidth();

        x = gtRectangle.getX();
        y = gtRectangle.getY();
        w = gtRectangle.getW();
        h = gtRectangle.getH();
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
        return new com.golftec.teaching.videoUtil.drawingTool.GTRectangle(this);
    }

    public GTRectangle() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle";
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    public GTRectangle(Graphics2D gc) {
        this.gc = gc;
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
    public void drawIt(Graphics2D gc) {
        gc.drawRect(this.x.intValue(), this.y.intValue(),
                    this.w.intValue(), this.h.intValue());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return new BoundingBox(this.x, this.y, this.w, this.h);
    }

    @Override
    public boolean isCollision(BoundingBox boundingBox) {
        return false;
    }

    @Override
    public boolean isCollision(Point2D point) {
        BoundingBox bb1 = new BoundingBox(this.x - 4, this.y - 4, this.w + 8, this.h + 8);
        BoundingBox bb2 = new BoundingBox(this.x + 4, this.y + 4, this.w - 8, this.h - 8);

        return bb1.contains(point) && !bb2.contains(point);
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {
        this.x = me.getX();
        this.y = me.getY();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        double x2 = me.getX();
        double y2 = me.getY();
        double x1 = this.x;
        double y1 = this.y;

        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);

        this.w = Math.abs(x2 - x1);
        this.h = Math.abs(y2 - y1);

        draw();
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
