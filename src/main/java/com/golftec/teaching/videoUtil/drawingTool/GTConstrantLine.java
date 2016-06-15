package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;

public class GTConstrantLine extends GTLine implements Serializable{



    public GTConstrantLine(GraphicsContext gc) {
        super(gc);
    }

    public GTConstrantLine() {
        super();
        type = "GTConstrantLine";
    }

    public GTConstrantLine(GTColor color) {
        super();
        this.stroke = color;
        type = "GTConstrantLine";
    }

    @Override
    public GTShape clone() {
        GTConstrantLine gtConstrantLine = new GTConstrantLine();
        gtConstrantLine.gc = gc;
        gtConstrantLine.isFinished = isFinished;
        gtConstrantLine.stroke = stroke;
        gtConstrantLine.lineWidth = lineWidth;
        gtConstrantLine.type = type;
        gtConstrantLine.highLightStroke = highLightStroke;
        gtConstrantLine.highLightlineWidth = highLightlineWidth;

        gtConstrantLine.startX = startX;
        gtConstrantLine.startY = startY;
        gtConstrantLine.endX = endX;
        gtConstrantLine.endX = endX;
        gtConstrantLine.endY = endY;
        gtConstrantLine.a = a;
        gtConstrantLine.b = b;
        return gtConstrantLine;
    }

    @Override
    public void consumeMouseDragged(GTMouseEvent me) {
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
