package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.annotation.HighLightLayer;
import com.golftec.teaching.videoUtil.annotation.MainLayer;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;

public class GTErase implements IToolable {
    MainLayer mainCanvasBoard = null;
    HighLightLayer highLightCanvasBoard = null;

    public GTErase(MainLayer mainCanvasBoard) {
        this.mainCanvasBoard = mainCanvasBoard;
    }

    public void setHighLightCanvasBoard(HighLightLayer highLightCanvasBoard){
        this.highLightCanvasBoard = highLightCanvasBoard;
    }

    @Override
    public void consumeMousePress(GTMouseEvent me) {
        GTShape gtShape = mainCanvasBoard.get(new Point2D(me.getX(), me.getY()));
        if (gtShape != null) {
            mainCanvasBoard.remove(gtShape.id);
            highLightCanvasBoard.removeAll();
        }
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
