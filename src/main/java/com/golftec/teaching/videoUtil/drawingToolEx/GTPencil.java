package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.GTPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GTPencil extends GTShape {

    private List<GTPoint> point2DList = null;

    public GTPencil() {
        point2DList = new ArrayList<GTPoint>();
        this.lineWidth = 3;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTPencil";
    }

    public GTPencil(com.golftec.teaching.videoUtil.drawingTool.GTPencil gtPencil) {
        id = gtPencil.id;
        isFinished = gtPencil.isFinished;
        color = gtPencil.getStroke();
        lineWidth = (float) gtPencil.getLineWidth();
        type = gtPencil.getType();
        highLightColor = gtPencil.getHighLightStroke();
        highLightlineWidth = (float) gtPencil.getHighLightlineWidth();
        point2DList = gtPencil.getPoint2DList();
    }

    public List<GTPoint> getPoint2DList(){
        return this.point2DList;
    }

    public GTPencil(GTColor color) {
        point2DList = new ArrayList<GTPoint>();
        this.lineWidth = 3;
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTPencil";
    }

    public GTPencil(Graphics2D gc) {
        this.gc = gc;
        point2DList = new ArrayList<GTPoint>();
    }

    @Override
    public void print() {
//        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTPencil(this);
    }

    @Override
    public void drawIt(Graphics2D gc) {
        for (int i = 0; i < point2DList.size() - 1; i++) {
            GTPoint next = point2DList.get(i + 1);
            gc.drawLine((int) point2DList.get(i).getX(), (int) point2DList.get(i).getY(),
                        (int) next.getX(), (int) next.getY());
        }
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
        point2DList.add(new GTPoint(me.getX(), me.getY()));
        draw();
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        point2DList.add(new GTPoint(me.getX(), me.getY()));
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

    @Override
    public double perimeter() {
        return 0;
    }

    @Override
    public double area() {
        return 0;
    }
}
