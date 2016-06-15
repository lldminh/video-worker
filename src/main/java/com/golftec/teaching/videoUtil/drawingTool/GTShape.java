package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.UUID;

abstract public class GTShape implements IToolable, Serializable {
    public UUID id = null;
    transient public boolean isFinished = false;
    transient protected GraphicsContext gc = null;
    //default setting to drawingBoard
    protected GTColor stroke = GTColor.GREEN;
//    protected Color fill = Color.GRAY;
    protected double lineWidth = 2;
    protected String type = "";
    protected GTColor highLightStroke = GTColor.RED;
    protected double highLightlineWidth = 5;
    transient protected BoundingBox boundingBox = null;
    protected DateTime createdDate = new DateTime(DateTimeZone.UTC);

    public DateTime getCreatedDate(){
        return this.createdDate;
    }

    public GTShape() {
        id = UUID.randomUUID();
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public double getLineWidth(){
        return this.lineWidth;
    }

    public String getType(){
        return this.type;
    }

    public double getHighLightlineWidth(){
        return this.highLightlineWidth;
    }

    public void setLineWidth(double lineWidth){
        this.lineWidth = lineWidth;
    }

    public GTColor getHighLightStroke(){
        return this.highLightStroke;
    }

    public void draw() {
        gc.setLineWidth(this.lineWidth);
        gc.setStroke(Common.getColor(this.stroke) );
        gc.setFill(Common.getColor(this.stroke));
        this.drawIt(gc);
    }

    public GTColor getStroke() {
        return this.stroke;
    }

    public void setStroke(GTColor color) {
        this.stroke = color;
    }

    public void draw(GraphicsContext gc) {
        gc.setLineWidth(this.lineWidth);
        gc.setStroke(Common.getColor(this.stroke));
        gc.setFill(Common.getColor(this.stroke));
        this.drawIt(gc);
    }

    public void draw(GraphicsContext gc, Color stroke) {
        gc.setLineWidth(this.lineWidth);
        gc.setFill(Common.getColor(this.stroke));
        gc.setStroke(stroke);
        this.drawIt(gc);
    }

    public void draw(GraphicsContext gc, Color stroke, Color fill) {
        gc.setLineWidth(this.lineWidth);
        gc.setStroke(stroke);
        gc.setFill(fill);
        this.drawIt(gc);
    }

    public void draw(GraphicsContext gc, Color stroke, Color fill, double lineWidth) {
        gc.setStroke(stroke);
        gc.setFill(fill);
        gc.setLineWidth(lineWidth);
        this.drawIt(gc);
    }

    public void draw(GraphicsContext gc, Color stroke, double lineWidth) {
        gc.setStroke(stroke);
        gc.setLineWidth(lineWidth);
        this.drawIt(gc);
    }

    public void highLight(GraphicsContext gc) {
        this.draw(gc, Common.getColor(this.highLightStroke), this.highLightlineWidth);
        this.draw(gc);
    }

    public abstract void drawIt(GraphicsContext gc);

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

    public abstract GTShape clone();

    public abstract com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx();

}
