package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.io.Serializable;
import java.util.UUID;

public class GTLine extends GTShape implements Serializable {

    protected Double startX = null;
    protected Double startY = null;
    protected Double endX = null;
    protected Double endY = null;
    protected Double a = null;
    protected Double b = null;

    public GTLine(double startX, double startY,
                  double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTLine";
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTLine(this);
    }

    public Double getA(){
        return this.a;
    }

    public Double getB(){
        return this.b;
    }

    public GTLine() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTLine";
    }

    public GTLine(com.golftec.teaching.videoUtil.drawingTool.GTLine gtLine) {
        try {
            if (gtLine != null) {
                id = gtLine.id;
                isFinished = gtLine.isFinished;
                color = gtLine.getStroke();
                lineWidth = (float) gtLine.getLineWidth();
                type = gtLine.getType();
                highLightColor = gtLine.getHighLightStroke();
                highLightlineWidth = (float) gtLine.getHighLightlineWidth();

                startX = gtLine.getStartX();
                startY = gtLine.getStartY();
                endX = gtLine.getEndX();
                endY = gtLine.getEndY();
                a = gtLine.getA();
                b = gtLine.getB();
            }
        } catch (Exception ex) {
            System.out.println("GTLine: " + ex);
        }
    }

    public GTLine(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTLine";
    }

    public GTLine(Graphics2D gc) {
        this.gc = gc;
    }

    public double getStartX() {
        return this.startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return this.startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public double getEndX() {
        return this.endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    @Override
    public void print() {
        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f%n", startX, startY, endX, endY);
    }

    @Override
    public void drawIt(Graphics2D gc) {
        if (startX == null || startY == null
            || endX == null || endY == null) {
            return;
        }
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        gc.drawLine(startX.intValue(), startY.intValue(), endX.intValue(), endY.intValue());
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
        if (startX == null || startY == null
            || endX == null || endY == null) {
            return false;
        }

        double x1 = this.startX;
        double y1 = this.startY;

        double x2 = this.endX;
        double y2 = this.endY;

        if (point.getX() > x1 && point.getX() > x2) {
            return false;
        }

        if (point.getX() < x1 && point.getX() < x2) {
            return false;
        }

        a = (y2 - y1) / (x2 - x1);
        b = y1 - a * x1;

        double b1 = point.getY() - a * point.getX();

        if (Math.abs(b1 - b) < 10) {
            return true;
        }

        return false;
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
        System.out.println("consumeMousePress");
        this.startX = me.getX();
        this.startY = me.getY();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        System.out.println("consumeMouseDragged");
        this.endX = me.getX();
        this.endY = me.getY();
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
        System.out.println("consumeMouseReleased");
        this.isFinished = true;
    }
}
