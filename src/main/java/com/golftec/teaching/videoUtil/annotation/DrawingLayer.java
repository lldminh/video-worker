package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.drawingTool.*;
import com.golftec.teaching.videoUtil.history.DrawingLayerHistory;
import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.MouseEventType;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DrawingLayer {
    private Canvas canvas = null;
    private Pane container = null;
    private IToolable iToolable = null;
    private MainLayer mainLayer = null;
    private GTColor stroke = GTColor.GREEN; //default


    private DrawingLayerHistory drawingLayerHistory = null;

    public DrawingLayer(Canvas canvas, MainLayer mainLayer, Pane container) {
        this.canvas = canvas;
        this.container = container;
        this.mainLayer = mainLayer;

//        init();
    }

    public DrawingLayer(Canvas canvas) {
        this.canvas = canvas;

//        init();
    }

    public void setMainLayer(MainLayer mainLayer){
        this.mainLayer = mainLayer;
    }

    public void clear(){
        canvas.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);
    }

    public DrawingLayerHistory getDrawingLayerHistory(){
        return this.drawingLayerHistory;
    }

    public void setDrawingLayerHistory(DrawingLayerHistory drawingLayerHistory){
        this.drawingLayerHistory = drawingLayerHistory;
    }

    public void showHistory(){
        this.drawingLayerHistory.showHistory();
    }

    public GTColor getStroke() {
        return this.stroke;
    }

    public void setStroke(GTColor color){
        this.stroke = color;
        if(this.iToolable instanceof GTShape){
            ((GTShape)this.iToolable).setStroke(color);
        }
    }

    public void setiToolable(IToolable iToolable) {
        System.out.println("iToolable: " + iToolable);
        this.iToolable = iToolable;
        if(this.iToolable instanceof GTShape){
            ((GTShape) this.iToolable).setGc(canvas.getGraphicsContext2D());
        }
    }

    public GTShape createNewInstance(IToolable iToolable) {
        GTShape res = null;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (iToolable instanceof GTConstrantArrow) {
            res = new GTConstrantArrow(gc);
        } else if (iToolable instanceof GTArrow) {
            res = new GTArrow(gc);
        } else if (iToolable instanceof GTConstrantLine) {
            res = new GTConstrantLine(gc);
        } else if (iToolable instanceof GTLine) {
            res = new GTLine(gc);
        } else if (iToolable instanceof GTCircle) {
            res = new GTCircle(gc);
        } else if (iToolable instanceof GTOval) {
            res = new GTOval(gc);
        } else if (iToolable instanceof GTSquare) {
            res = new GTSquare(gc);
        } else if (iToolable instanceof GTRectangle) {
            res = new GTRectangle(gc);
        } else if (iToolable instanceof GTAngle) {
            res = new GTAngle(gc);
        }else if (iToolable instanceof GTPencil) {
            res = new GTPencil(gc);
        }

        res.setStroke(this.stroke);

        return res;
    }

    public void consumeEvent(GTMouseEvent mouseEvent, MouseEventType type){
        System.out.println("consumeEvent");
        DrawingLayerEvent drawingLayerEvent = new DrawingLayerEvent();
        drawingLayerEvent.mouseEvent = mouseEvent;
        drawingLayerEvent.mouseEventType = type;

        drawingLayerHistory.add(drawingLayerEvent);

        switch (type){
            case MOUSE_DRAG_RELEASED:{
                if (iToolable != null) {
                    iToolable.consumeMouseDragReleased(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }
            case MOUSE_DRAGGED:{
                if (iToolable != null) {
                    canvas.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);
                    iToolable.consumeMouseDragged(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }
            case MOUSE_MOVED:{
                if (iToolable instanceof GTErase) {
                    //fire event on Main Canvas Board
                    mainLayer.consumeMouseMoved(mouseEvent);
                }

                if (iToolable != null) {
                    canvas.getGraphicsContext2D().clearRect(0, 0, 2000, 2000);
                    iToolable.consumeMouseMoved(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }

                }
                break;
            }
            case MOUSE_PRESS:{
                if (iToolable != null) {
                    iToolable.consumeMousePress(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }
            case MOUSE_RELEASED:{
                if (iToolable != null) {
                    iToolable.consumeMouseReleased(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                            iToolable = createNewInstance(iToolable);
                        }
                    }
                }
            }
        }
    }

    private void init() {

        container.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeEvent(mouseEvent, MouseEventType.MOUSE_PRESS);
            }
        });

        container.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeEvent(mouseEvent, MouseEventType.MOUSE_DRAGGED);
            }
        });

        container.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeEvent(mouseEvent, MouseEventType.MOUSE_MOVED);
            }
        });

        container.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeEvent(mouseEvent, MouseEventType.MOUSE_DRAG_RELEASED);
            }
        });

        container.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeEvent(mouseEvent, MouseEventType.MOUSE_RELEASED);
            }
        });
    }

    public GTShape getShape(){
        return (GTShape)iToolable;
    }
}
