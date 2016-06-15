package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTConstrantArrow extends GTArrow implements Serializable{



    public GTConstrantArrow(GraphicsContext gc){
        super(gc);
    }

    public GTConstrantArrow(){
        super();
        type = "GTConstrantArrow";
    }

    public GTConstrantArrow(GTColor stroke){
        super();
        this.stroke = stroke;
        type = "GTConstrantArrow";
    }

    @Override
    public GTShape clone() {
        GTConstrantArrow gtConstrantArrow = new GTConstrantArrow();
        gtConstrantArrow.gc = gc;
        gtConstrantArrow.isFinished = isFinished;
        gtConstrantArrow.stroke = stroke;
        gtConstrantArrow.lineWidth = lineWidth;
        gtConstrantArrow.type = type;
        gtConstrantArrow.highLightStroke = highLightStroke;
        gtConstrantArrow.highLightlineWidth = highLightlineWidth;

        gtConstrantArrow.startX = startX;
        gtConstrantArrow.startY = startY;
        gtConstrantArrow.endX = endX;
        gtConstrantArrow.endX = endX;
        gtConstrantArrow.endY = endY;
        gtConstrantArrow.a = a;
        gtConstrantArrow.b = b;
        return gtConstrantArrow;
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
        if(me == null){
            return;
        }

        double x2 = me.getX();
        double y2 = me.getY();

        double x1 = this.startX;
        double y1 = this.startY;

        double d1 = new Point2D(x1, y1).distance(new Point2D(x2, y2)); // canh huyen
        double y2y1 = Math.abs(y2 - y1);
        double x2x1 = Math.abs(x2 - x1);

        double angle1 = Math.toDegrees(Math.asin(y2y1 / d1));
        double angle2 = Math.toDegrees(Math.asin(x2x1 / d1));
        if (angle1 < angle2) {
            this.endX = x2;
            this.endY = y1;
        } else {
            this.endX = x1;
            this.endY = y2;
        }
        draw();
    }
}
