package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.GTPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;

public class GTCursor extends GTShape implements Serializable{

    private GTPoint point = null;

    public GTCursor(GTColor color){
        this.lineWidth = 3;
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTCursor";
    }

    public GTCursor(com.golftec.teaching.videoUtil.drawingTool.GTCursor gtCursor) {
        id = gtCursor.id;
        isFinished = gtCursor.isFinished;
        color = gtCursor.getStroke();
        lineWidth = (float) gtCursor.getLineWidth();
        type = gtCursor.getType();
        highLightColor = gtCursor.getHighLightStroke();
        highLightlineWidth = (float) gtCursor.getHighLightlineWidth();

        point = gtCursor.getPoint();
    }

    public GTCursor(){
        this.lineWidth = 3;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTCursor";
    }

    public GTPoint getPoint(){
        return this.point;
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTCursor(this);
    }

    @Override
    public void drawIt(Graphics2D gc) {
        gc.drawLine((int) point.getX(), (int) point.getY(), (int) point.getX(), (int) point.getY());
    }

    @Override
    public void highLight(Graphics2D gc) {

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
        return false;
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseMoved(GTMouseEvent me) {
        point = new GTPoint(me.getX(), me.getY());
        draw();
    }

    @Override
    public void consumeMouseDragReleased(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseReleased(GTMouseEvent me) {

    }

    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }
}
