package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTArrow extends GTLine implements Serializable{

    private static final double ARROW_LENGTH = 15;

    public GTArrow() {
        super();
    }

    public GTArrow(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTArrow";
    }

    public GTArrow(com.golftec.teaching.videoUtil.drawingToolEx.GTArrow gtArrow) {
        id = gtArrow.id;
        isFinished = gtArrow.isFinished;
        stroke = gtArrow.getColor();
        lineWidth = gtArrow.getLineWidth();
        type = gtArrow.getType();
        highLightStroke = gtArrow.getHighLightColor();
        highLightlineWidth = gtArrow.getHighLightlineWidth();

        startX = gtArrow.getStartX();
        startY = gtArrow.getStartY();
        endX = gtArrow.getEndX();
        endY = gtArrow.getEndY();
        a = gtArrow.getA();
        b = gtArrow.getB();
    }

    public GTArrow(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        if(this.startX == null
                || this.startY == null
                || this.endX == null
                || this.startY == null){
            return;
        }

        gc.strokeLine(startX, startY, endX, endY);

        double x1 = this.startX;
        double y1 = this.startY;
        double x2 = this.endX;
        double y2 = this.endY;

        double arrowlenght = ARROW_LENGTH;
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double unrotatedX = x2 + ((x1 - x2) / distance) * arrowlenght;
        double unrotatedY = y2 + ((y1 - y2) / distance) * arrowlenght;

        Point2D rotated1 = new Point2D(x2 + (unrotatedX - x2) * Math.cos(0.5) - (unrotatedY - y2) * Math.sin(0.5), y2 + (unrotatedX - x2) * Math.sin(0.5) + (unrotatedY - y2) * Math.cos(0.5));
        Point2D rotated2 = new Point2D(x2 + (unrotatedX - x2) * Math.cos(-0.5) - (unrotatedY - y2) * Math.sin(-0.5), y2 + (unrotatedX - x2) * Math.sin(-0.5) + (unrotatedY - y2) * Math.cos(-0.5));

        gc.strokeLine(rotated1.getX(), rotated1.getY(), x2, y2);
        gc.strokeLine(rotated2.getX(), rotated2.getY(), x2, y2);
    }

    @Override
    public GTShape clone() {
        GTArrow gtArrow = new GTArrow();
        gtArrow.gc = gc;
        gtArrow.isFinished = isFinished;
        gtArrow.stroke = stroke;
        gtArrow.lineWidth = lineWidth;
        gtArrow.type = type;
        gtArrow.highLightStroke = highLightStroke;
        gtArrow.highLightlineWidth = highLightlineWidth;

        gtArrow.startX = startX;
        gtArrow.startY = startY;
        gtArrow.endX = endX;
        gtArrow.endX = endX;
        gtArrow.endY = endY;
        gtArrow.a = a;
        gtArrow.b = b;
        return gtArrow;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTArrow gtArrow = new com.golftec.teaching.videoUtil.drawingToolEx.GTArrow(this);
        return gtArrow;
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
