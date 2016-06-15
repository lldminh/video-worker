package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;

import java.io.Serializable;

public class GTAngle extends GTShape implements Serializable {

    protected Double x1, y1 = null;
    protected Double x2, y2 = null;
    protected Double x3, y3 = null;
    private Status status = Status.START;

    public GTAngle() {
        super();
        type = "GTAngle";
    }

    public GTAngle(GTColor stroke) {
        super();
        this.stroke = stroke;
        type = "GTAngle";
    }

    public GTAngle(com.golftec.teaching.videoUtil.drawingToolEx.GTAngle gtAngle) {
        id = gtAngle.id;
        isFinished = gtAngle.isFinished;
        stroke = gtAngle.getColor();
        lineWidth = gtAngle.getLineWidth();
        type = gtAngle.getType();
        highLightStroke = gtAngle.getHighLightColor();
        highLightlineWidth = gtAngle.getHighLightlineWidth();

        x1 = gtAngle.getX1();
        x2 = gtAngle.getX2();
        x3 = gtAngle.getX3();
        y1 = gtAngle.getY1();
        y2 = gtAngle.getY2();
        y3 = gtAngle.getY3();
    }

    public Status getStatus(){
        return this.status;
    }

    public Double getX1(){
        return this.x1;
    }

    public Double getY1(){
        return this.y1;
    }

    public Double getX2(){
        return this.x2;
    }

    public Double getY2(){
        return this.y2;
    }

    public Double getX3(){
        return this.x3;
    }

    public Double getY3(){
        return this.y3;
    }

    public GTAngle(GraphicsContext gc) {
        this.gc = gc;
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

    private void drawArc(GraphicsContext gc) {
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

        gc.setTextAlign(TextAlignment.RIGHT);
        gc.fillText(Double.toString(Math.round(Math.abs(angleDiff(aa, ad)))) + "°", this.x2, this.y2);
        gc.strokeArc(x0 - ra, y0 - ra, 2 * ra, 2 * ra, aa, angleDiff(aa, ad), ArcType.OPEN);
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
    public GTShape clone() {
        GTAngle gtAngle = new GTAngle();
        gtAngle.gc = gc;
        gtAngle.isFinished = isFinished;
        gtAngle.stroke = stroke;
        gtAngle.lineWidth = lineWidth;
        gtAngle.type = type;
        gtAngle.highLightStroke = highLightStroke;
        gtAngle.highLightlineWidth = highLightlineWidth;
        gtAngle.x1 = x1;
        gtAngle.y1 = y1;
        gtAngle.x2 = x2;
        gtAngle.y2 = y2;
        gtAngle.x3 = x3;
        gtAngle.y3 = y3;
        gtAngle.status = status;
        return gtAngle;
    }

    @Override
    public com.golftec.teaching.videoUtil.drawingToolEx.GTShape getGTShapeEx() {
        com.golftec.teaching.videoUtil.drawingToolEx.GTAngle gtAngle = new com.golftec.teaching.videoUtil.drawingToolEx.GTAngle(this);
        return gtAngle;
    }

    @Override
    public void drawIt(GraphicsContext gc) {
        if (this.x1 != null && this.y1 != null && this.x2 != null && this.y2 != null
            && this.x3 != null && this.y3 != null){
            gc.strokeLine(this.x1, this.y1, this.x2, this.y2);
            gc.strokeLine(this.x2, this.y2, this.x3, this.y3);
            this.drawArc(gc);
        }

        if (this.x1 != null && this.y1 != null && this.x2 != null && this.y2 != null) {
            gc.strokeLine(this.x1, this.y1, this.x2, this.y2);
        }
//        switch (this.status) {
//            case CONTINUE: {
//                gc.strokeLine(this.x1, this.y1, this.x2, this.y2);
//                break;
//            }
//            case END: {
//                gc.strokeLine(this.x1, this.y1, this.x2, this.y2);
//                gc.strokeLine(this.x2, this.y2, this.x3, this.y3);
//                this.drawArc(gc);
//                break;
//            }
//        }
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
        GTLine line1 = new GTLine(x1, y1, x2, y2);
        GTLine line2 = new GTLine(x2, y2, x3, y3);
        return line1.isCollision(point) || line2.isCollision(point);
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {
        switch (this.status) {
            case START: {
                this.x1 = me.getX();
                this.y1 = me.getY();
                this.status = Status.CONTINUE;
                break;
            }
            case CONTINUE: {
                this.x2 = me.getX();
                this.y2 = me.getY();
                this.draw();
                this.status = Status.END;
                break;
            }
            case END: {
                this.x3 = me.getX();
                this.y3 = me.getY();
                this.draw();
                this.isFinished = true;
                break;
            }
        }
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseMoved(GTMouseEvent me) {
        if (status == Status.CONTINUE) {
            this.x2 = me.getX();
            this.y2 = me.getY();
            this.draw();
        } else if (status == Status.END) {
            this.x3 = me.getX();
            this.y3 = me.getY();
            this.draw();
        }
    }

    @Override
    public void consumeMouseDragReleased(GTMouseEvent me) {

    }

    @Override
    public void consumeMouseReleased(GTMouseEvent me) {

    }

    public enum Status {
        START,
        CONTINUE,
        END
    }
}
