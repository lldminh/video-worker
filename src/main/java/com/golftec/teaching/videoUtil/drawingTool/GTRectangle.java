package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTRectangle extends GTShape implements Serializable {

    protected double x = 0;
    protected double y = 0;
    protected double w = 0;
    protected double h = 0;

    protected double x1, y1 = 0;
    protected double x2, y2 = 0;

    public GTRectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        type = "GTRectangle";
    }

    public GTRectangle(com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle gtRectangle) {
        id = gtRectangle.id;
        isFinished = gtRectangle.isFinished;
        stroke = gtRectangle.getColor();
        lineWidth = (float) gtRectangle.getLineWidth();
        type = gtRectangle.getType();
        highLightStroke = gtRectangle.getHighLightColor();
        highLightlineWidth = (float) gtRectangle.getHighLightlineWidth();

        x = gtRectangle.getX();
        y = gtRectangle.getY();
        w = gtRectangle.getW();
        h = gtRectangle.getH();
    }

    public GTRectangle(GTColor stroke) {
        this.stroke = stroke;
        type = "GTRectangle";
    }

    public GTRectangle() {
        super();
        type = "GTRectangle";
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

    public GTRectangle(GraphicsContext gc) {
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
    public void drawIt(GraphicsContext gc) {

//        print();
        gc.strokeRoundRect(this.x, this.y, this.w, this.h, 10, 10);
    }

    @Override
    public GTShape clone() {
        GTRectangle gtRectangle = new GTRectangle();
        gtRectangle.gc = gc;
        gtRectangle.isFinished = isFinished;
        gtRectangle.stroke = stroke;
        gtRectangle.lineWidth = lineWidth;
        gtRectangle.type = type;
        gtRectangle.highLightStroke = highLightStroke;
        gtRectangle.highLightlineWidth = highLightlineWidth;

        gtRectangle.x = x;
        gtRectangle.y = y;
        gtRectangle.w = w;
        gtRectangle.h = h;
        return gtRectangle;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle gtRectangle =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTRectangle(this);
        return gtRectangle;
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
        this.x1 = me.getX();
        this.y1 = me.getY();
    }

    public void print() {
        System.out.format("GTRectangle: x = %f, y = %f, w = %f, h = %f", x, y, w, h);
        System.out.println();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        this.x2 = me.getX();
        this.y2 = me.getY();

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
