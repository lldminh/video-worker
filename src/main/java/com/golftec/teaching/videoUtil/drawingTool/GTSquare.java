package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTSquare extends GTRectangle implements Serializable{

    public GTSquare(double x, double y, double width) {
        super(x, y, width, width);
    }

    public GTSquare() {
        super();
        type = "GTSquare";
    }

    public GTSquare(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTSquare";
    }

    public GTSquare(com.golftec.teaching.videoUtil.drawingToolEx.GTSquare gtSquare) {
        id = gtSquare.id;
        isFinished = gtSquare.isFinished;
        stroke = gtSquare.getColor();
        lineWidth = (float) gtSquare.getLineWidth();
        type = gtSquare.getType();
        highLightStroke = gtSquare.getHighLightColor();
        highLightlineWidth = (float) gtSquare.getHighLightlineWidth();

        x = gtSquare.getX();
        y = gtSquare.getY();
        w = gtSquare.getW();
        h = gtSquare.getH();
    }

    public GTSquare(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTSquare gtSquare =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTSquare(this);
        return gtSquare;
    }

    @Override
    public GTShape clone() {
        GTSquare gtSquare = new GTSquare();
        gtSquare.gc = gc;
        gtSquare.isFinished = isFinished;
        gtSquare.stroke = stroke;
        gtSquare.lineWidth = lineWidth;
        gtSquare.type = type;
        gtSquare.highLightStroke = highLightStroke;
        gtSquare.highLightlineWidth = highLightlineWidth;

        gtSquare.x = x;
        gtSquare.y = y;
        gtSquare.w = w;
        gtSquare.h = h;
        return gtSquare;
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        this.x2 = me.getX();
        this.y2 = me.getY();

        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);

        this.w = this.h = Math.max(Math.abs(y2 - y1), Math.abs(x2 - x1));

        draw();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
