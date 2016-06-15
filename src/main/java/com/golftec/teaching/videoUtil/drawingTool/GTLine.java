package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.DoubleSummaryStatistics;

public class GTLine extends GTShape implements Serializable{

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
        type = "GTLine";
    }
    public GTLine() {
        super();
        type = "GTLine";
    }

    public GTLine(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTLine";
    }

    public GTLine(com.golftec.teaching.videoUtil.drawingToolEx.GTLine gtLine) {
        try {
            if (gtLine != null) {
                id = gtLine.id;
                isFinished = gtLine.isFinished;
                stroke = gtLine.getColor();
                lineWidth = (float) gtLine.getLineWidth();
                type = gtLine.getType();
                highLightStroke = gtLine.getHighLightColor();
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

    public GTLine(GraphicsContext gc) {
        this.gc = gc;
    }

    public Double getStartX() {
        return this.startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public Double getStartY() {
        return this.startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public Double getEndX() {
        return this.endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public Double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public Double getA(){
        return this.a;
    }

    public Double getB(){
        return this.b;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTLine gtLine =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTLine(this);
        return gtLine;
    }

    @Override
    public GTShape clone() {
        GTLine gtLine = new GTLine();
        gtLine.gc = gc;
        gtLine.isFinished = isFinished;
        gtLine.stroke = stroke;
        gtLine.lineWidth = lineWidth;
        gtLine.type = type;
        gtLine.highLightStroke = highLightStroke;
        gtLine.highLightlineWidth = highLightlineWidth;

        gtLine.startX = startX;
        gtLine.startY = startY;
        gtLine.endX = endX;
        gtLine.endX = endX;
        gtLine.endY = endY;
        gtLine.a = a;
        gtLine.b = b;
        return gtLine;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        if (startX == null || startY == null
                || endX == null || endY == null) {
            return;
        }
        gc.strokeLine(startX, startY, endX, endY);

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
        this.startX = me.getX();
        this.startY = me.getY();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
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
        this.isFinished = true;
    }
}
