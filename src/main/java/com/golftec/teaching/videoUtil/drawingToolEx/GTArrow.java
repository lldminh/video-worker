package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.drawingTool.*;
import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.golftec.teaching.videoUtil.util.GTColor;

import java.awt.*;
import java.io.Serializable;

public class GTArrow extends GTLine implements Serializable{

    private static final double ARROW_LENGTH = 15;

    public GTArrow() {
        super();
    }

    public GTArrow(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTArrowEx";
    }

    public GTArrow(Graphics2D gc) {
        this.gc = gc;
    }

    public GTArrow(com.golftec.teaching.videoUtil.drawingTool.GTArrow gtArrow) {
        id = gtArrow.id;
        isFinished = gtArrow.isFinished;
        color = gtArrow.getStroke();
        lineWidth = (float) gtArrow.getLineWidth();
        type = gtArrow.getType();
        highLightColor = gtArrow.getHighLightStroke();
        highLightlineWidth = (float) gtArrow.getHighLightlineWidth();

        startX = gtArrow.getStartX();
        startY = gtArrow.getStartY();
        endX = gtArrow.getEndX();
        endY = gtArrow.getEndY();
        a = gtArrow.getA();
        b = gtArrow.getB();
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTArrow(this);
    }

    @Override
    public void drawIt(Graphics2D gc) {
        if(this.startX == null
                || this.startY == null
                || this.endX == null
                || this.startY == null){
            return;
        }

        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        gc.drawLine(startX.intValue(), startY.intValue(), endX.intValue(), endY.intValue());

        double x1 = this.startX;
        double y1 = this.startY;
        double x2 = this.endX;
        double y2 = this.endY;

        double arrowlenght = ARROW_LENGTH;
        double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double unrotatedX = x2 + ((x1 - x2) / distance) * arrowlenght;
        double unrotatedY = y2 + ((y1 - y2) / distance) * arrowlenght;



        double rotated1X = x2 + (unrotatedX - x2) * Math.cos(0.5) - (unrotatedY - y2) * Math.sin(0.5);
        double rotated1Y = y2 + (unrotatedX - x2) * Math.sin(0.5) + (unrotatedY - y2) * Math.cos(0.5);
        Point rotated1 = new Point((int)rotated1X, (int)rotated1Y);

        double rotated2X = x2 + (unrotatedX - x2) * Math.cos(-0.5) - (unrotatedY - y2) * Math.sin(-0.5);
        double rotated2Y = y2 + (unrotatedX - x2) * Math.sin(-0.5) + (unrotatedY - y2) * Math.cos(-0.5);
        Point rotated2 = new Point((int)rotated2X, (int)rotated2Y);

        gc.drawLine((int)rotated1.getX(), (int)rotated1.getY(), (int)x2, (int)y2);
        gc.drawLine((int)rotated2.getX(), (int)rotated2.getY(), (int)x2, (int)y2);
    }

    public Double getA(){
        return this.a;
    }

    public Double getB(){
        return this.b;
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
