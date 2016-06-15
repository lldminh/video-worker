package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.GTPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTCursor extends GTShape implements Serializable{

    private GTPoint point = null;

    public GTCursor(GTColor stroke){
        this.lineWidth = 3;
        this.stroke = stroke;
        type = "GTCursor";
    }

    public GTCursor(){
        this.lineWidth = 3;
        type = "GTCursor";
    }

    public GTCursor(com.golftec.teaching.videoUtil.drawingToolEx.GTCursor gtCursor) {
        id = gtCursor.id;
        isFinished = gtCursor.isFinished;
        stroke = gtCursor.getColor();
        lineWidth = (float) gtCursor.getLineWidth();
        type = gtCursor.getType();
        highLightStroke = gtCursor.getHighLightColor();
        highLightlineWidth = (float) gtCursor.getHighLightlineWidth();

        point = gtCursor.getPoint();
    }

    public GTPoint getPoint(){
        return this.point;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        gc.strokeLine(point.getX(), point.getY(), point.getX(), point.getY());
    }

    @Override
    public void highLight(GraphicsContext gc) {

    }

    @Override
    public GTShape clone() {
        GTCursor gtCursor = new GTCursor();
        gtCursor.gc = gc;
        gtCursor.isFinished = isFinished;
        gtCursor.stroke = stroke;
        gtCursor.lineWidth = lineWidth;
        gtCursor.type = type;
        gtCursor.highLightStroke = highLightStroke;
        gtCursor.highLightlineWidth = highLightlineWidth;

        gtCursor.point = point;
        return gtCursor;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTCursor gtCursor =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTCursor(this);
        return gtCursor;
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
