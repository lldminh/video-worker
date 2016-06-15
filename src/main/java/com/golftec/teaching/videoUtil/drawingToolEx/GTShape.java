package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.common.JsonSerializationExcluded;
import com.golftec.teaching.videoUtil.drawingTool.IToolable;
import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.UUID;

abstract public class GTShape implements IToolable, Serializable {

    public UUID id = null;
    transient public boolean isFinished = false;
    @JsonSerializationExcluded
    public String type = "";
    transient protected Graphics2D gc = null;
    //default setting to drawingBoard
    protected GTColor color = GTColor.GREEN;
    //    protected Color fill = Color.GRAY;
    protected float lineWidth = 2;
    protected GTColor highLightColor = GTColor.RED;
    protected float highLightlineWidth = 5;
    transient protected BoundingBox boundingBox = null;

    public GTShape() {
        id = UUID.randomUUID();
    }

    public void setGc(Graphics2D gc) {
        this.gc = gc;
    }

    public GTColor getHighLightColor(){
        return this.highLightColor;
    }

    public float getHighLightlineWidth(){
        return this.highLightlineWidth;
    }

    public float getLineWidth(){
        return this.lineWidth;
    }

    public String getType(){
        return this.type;
    }

    public void draw() {
        gc.setStroke(new BasicStroke(lineWidth));
        gc.setColor(Common.getAWTColor(this.color));
        this.drawIt(this.gc);
    }

    public void draw(Graphics2D gc) {
        gc.setStroke(new BasicStroke(lineWidth));
        gc.setColor(Common.getAWTColor(this.color));
        this.drawIt(gc);
    }

    public void draw(Graphics2D gc, float lineWidth) {
        gc.setStroke(new BasicStroke(lineWidth));
        gc.setColor(Common.getAWTColor(this.color));
        this.drawIt(gc);
    }

    public void draw(Graphics2D gc, java.awt.Color color, float lineWidth) {
        gc.setStroke(new BasicStroke(lineWidth));
        gc.setColor(color);

        this.drawIt(gc);
    }

    public GTColor getColor() {
        return this.color;
    }

    public void setColor(GTColor color) {
        this.color = color;
    }

    public void highLight(Graphics2D gc) {
        this.draw(gc, Common.getAWTColor(this.highLightColor), this.highLightlineWidth);
        this.draw(gc);
    }

    public abstract void drawIt(Graphics2D gc);

    public BufferedImage getBufferedImage(int width, int height, int x, int y) {
        return null;
    }

    public abstract BoundingBox getBoundingBox();

    public abstract boolean isCollision(BoundingBox boundingBox);

    public abstract boolean isCollision(Point2D point);

    public abstract void consumeMousePress(GTMouseEvent me);

    public abstract void consumeMouseDragged(GTMouseEvent me);

    public abstract void consumeMouseMoved(GTMouseEvent me);

    public abstract void consumeMouseDragReleased(GTMouseEvent me);

    public abstract void consumeMouseReleased(GTMouseEvent me);

    public abstract double perimeter();

    public abstract double area();

    public abstract void print();

    public abstract com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape();


}
