package com.golftec.teaching.videoUtil.drawingToolEx;

import com.golftec.teaching.videoUtil.drawingTool.GTLine;
import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.Serializable;

public class GTAngle extends GTShape implements Serializable {

    protected Double x1, y1 = null;
    protected Double x2, y2 = null;
    protected Double x3, y3 = null;

    public GTAngle() {
        super();
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTAngle";
    }

    public GTAngle(GTColor color) {
        super();
        this.color = color;
        type = "com.golftec.teaching.videoUtil.drawingToolEx.GTAngle";
    }

    public GTAngle(Graphics2D gc) {
        this.gc = gc;
    }

    public GTAngle(com.golftec.teaching.videoUtil.drawingTool.GTAngle gtAngle) {
        id = gtAngle.id;
        isFinished = gtAngle.isFinished;
        color = gtAngle.getStroke();
        lineWidth = (float) gtAngle.getLineWidth();
        type = gtAngle.getType();
        highLightColor = gtAngle.getHighLightStroke();
        highLightlineWidth = (float) gtAngle.getHighLightlineWidth();

        x1 = gtAngle.getX1();
        x2 = gtAngle.getX2();
        x3 = gtAngle.getX3();
        y1 = gtAngle.getY1();
        y2 = gtAngle.getY2();
        y3 = gtAngle.getY3();
    }

    public Double getX1(){
        return this.x1;
    }

    public Double getX2(){
        return this.x2;
    }

    public Double getX3(){
        return this.x3;
    }

    public Double getY1(){
        return this.y1;
    }

    public Double getY2(){
        return this.y2;
    }

    public Double getY3(){
        return this.y3;
    }

    public GTAngle(double x1, double y1,
                   double x2, double y2,
                   double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    // Return the square of a float.
    static double sqr(double x) {
        return x * x;
    }

    // Find the angular difference between a and b, -180 <= diff < 180.
    static double angleDiff(double a, double b) {
        double d = b - a;
        while (d >= 180f) {
            d -= 360f;
        }
        while (d < -180f) {
            d += 360f;
        }
        return d;
    }

    private double dist0(double x, double y) {
        return Math.sqrt(sqr(x - this.x2) + sqr(y - this.y2));
    }

    // Return polar angle of any point relative to arc center.
    private double angle0(double x, double y) {
        return (double) Math.toDegrees(Math.atan2(this.y2 - y, x - this.x2));
    }

    private void drawArc(Graphics2D gc) {
        double x0 = this.x2, y0 = this.y2;   // Arc center. Subscript 0 used for center throughout.
        double xa = this.x1, ya = this.y1;   // Arc anchor point.  Subscript a for anchor.
        double xd = this.x3, yd = this.y3;

        double ra = dist0(xa, ya);
        if (ra > 50) {
            ra = 50;
        }
        double rd = dist0(xd, yd);

        // If either is zero there's nothing else to drawingBoard.
        if (ra == 0 || rd == 0) {
            return;
        }

        // Get the angles from center to points.
        double aa = angle0(xa, ya);
        double ad = angle0(xd, yd);  // (xb, yb) would work fine, too.

//        gc.setTextAlign(TextAlignment.RIGHT);
        FontMetrics metrics = gc.getFontMetrics();
        String text = Double.toString(Math.round(Math.abs(angleDiff(aa, ad)))) + "°";
        gc.drawString(text, this.x2.intValue() - metrics.stringWidth(text), this.y2.intValue());
        gc.drawArc((int) (x0 - ra), (int) (y0 - ra), (int) (2 * ra), (int) (2 * ra), (int) aa, (int)angleDiff(aa, ad));
    }

    @Override
    public void print() {
        System.out.format("GTLine: x1 = %f, y1 = %f, x2 = %f, y2 = %f, x3 = %f, y3 = %f", x1, y1, x2, y2, x3, y3);
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingTool.GTShape getGTShape() {
        return new com.golftec.teaching.videoUtil.drawingTool.GTAngle(this);
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
    public void drawIt(Graphics2D gc) {
        gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        if (this.x1 != null && this.y1 != null && this.x2 != null && this.y2 != null
            && this.x3 != null && this.y3 != null) {
            gc.drawLine(this.x1.intValue(), (int) this.y1.intValue(), this.x2.intValue(), this.y2.intValue());
            gc.drawLine(this.x2.intValue(), this.y2.intValue(), this.x3.intValue(), this.y3.intValue());
            this.drawArc(gc);
            return;
        }

        if (this.x1 != null && this.y1 != null && this.x2 != null && this.y2 != null) {
            gc.drawLine(this.x1.intValue(), this.y1.intValue(), this.x2.intValue(), this.y2.intValue());
            return;
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
        com.golftec.teaching.videoUtil.drawingTool.GTLine line1 = new com.golftec.teaching.videoUtil.drawingTool.GTLine(x1, y1, x2, y2);
        com.golftec.teaching.videoUtil.drawingTool.GTLine line2 = new GTLine(x2, y2, x3, y3);
        return line1.isCollision(point) || line2.isCollision(point);
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseMoved(GTMouseEvent me) {
    }

    @Override
    public void consumeMouseDragReleased(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseReleased(GTMouseEvent me) {

    }
}
