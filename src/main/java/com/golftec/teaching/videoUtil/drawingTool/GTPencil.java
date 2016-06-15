package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.GTPoint;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class GTPencil extends GTShape {

    private List<GTPoint> point2DList = null;

    public GTPencil() {
        point2DList = new ArrayList<GTPoint>();
        this.lineWidth = 3;
        type = "GTPencil";
    }

    public GTPencil(GTColor stroke) {
        point2DList = new ArrayList<GTPoint>();
        this.lineWidth = 3;
        this.stroke = stroke;
        type = "GTPencil";
    }

    public GTPencil(com.golftec.teaching.videoUtil.drawingToolEx.GTPencil gtPencil) {
        id = gtPencil.id;
        isFinished = gtPencil.isFinished;
        stroke = gtPencil.getColor();
        lineWidth = (float) gtPencil.getLineWidth();
        type = gtPencil.getType();
        highLightStroke = gtPencil.getHighLightColor();
        highLightlineWidth = (float) gtPencil.getHighLightlineWidth();

        point2DList = gtPencil.getPoint2DList();
    }

    @Override
    public GTShape clone() {
        GTPencil gtPencil = new GTPencil();
        gtPencil.gc = gc;
        gtPencil.isFinished = isFinished;
        gtPencil.stroke = stroke;
        gtPencil.lineWidth = lineWidth;
        gtPencil.type = type;
        gtPencil.highLightStroke = highLightStroke;
        gtPencil.highLightlineWidth = highLightlineWidth;

        gtPencil.point2DList = new ArrayList();
        if (point2DList != null) {
            for (int i = 0; i < point2DList.size(); i++) {
                gtPencil.point2DList.add(point2DList.get(i));
            }
        }

        return gtPencil;
    }

    public GTPencil(GraphicsContext gc) {
        this.gc = gc;
        point2DList = new ArrayList<GTPoint>();
    }

    public List<GTPoint> getPoint2DList() {
        return this.point2DList;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTPencil gtPencil =
                new com.golftec.teaching.videoUtil.drawingToolEx.GTPencil(this);
        return gtPencil;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        for (int i = 0; i < point2DList.size() - 1; i++) {
            GTPoint next = point2DList.get(i + 1);
            gc.strokeLine(point2DList.get(i).getX(), point2DList.get(i).getY(), next.getX(), next.getY());
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
