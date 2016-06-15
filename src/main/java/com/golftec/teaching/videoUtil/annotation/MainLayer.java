package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MainLayer {
    private Canvas canvas = null;
    private HighLightLayer highLightLayer = null;

    private List<GTShape> shapeList = new ArrayList<GTShape>();

    private DateTime lastUpdatedDate = null;

    public MainLayer(Canvas canvas) {
        this.canvas = canvas;
    }

    public GTShape get(Point2D point2D){
        for(int i = 0; i < shapeList.size(); i++){
            if(shapeList.get(i).isCollision(point2D)){
                return shapeList.get(i);
            }
        }
        return null;
    }

    public void setHighLightLayer(HighLightLayer highLightLayer){
        this.highLightLayer = highLightLayer;
    }

    public void consumeMouseMoved(GTMouseEvent me) {
        GTShape gtShape = get(new Point2D(me.getX(), me.getY()));
        if(gtShape != null){
            this.highLightLayer.removeAll();
            this.highLightLayer.pushShape(gtShape);
        }else {
            this.highLightLayer.removeAll();
        }
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 2000, 2000);
        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).draw(gc);
        }
    }

    public void remove(UUID uuid){
        for(int i = 0 ;i < shapeList.size(); i++){
            if(shapeList.get(i).id == uuid){
                shapeList.remove(i);
                draw();
            }
        }
    }

    private boolean isExist(UUID uuid){
        for(int i = 0 ;i < shapeList.size(); i++){
            if(shapeList.get(i).id == uuid){
                return true;
            }
        }
        return false;
    }

    public List<GTShape> getAll(){
        return this.shapeList;
    }

    public void pushShape(GTShape gtShape) {
        if(!this.isExist(gtShape.id)){
            lastUpdatedDate = new DateTime(DateTimeZone.UTC);
            shapeList.add(gtShape);
            draw();
        }
    }

    public DateTime getLastUpdatedDate(){
        return lastUpdatedDate;
    }

    public GTShape getLastAddedShape(){
        if(shapeList != null){
            if(shapeList.size() > 0){
                Collections.sort(shapeList, new Comparator<GTShape>() {
                    public int compare(GTShape o1, GTShape o2) {
                        if (o1 == null || o2 == null) {
                            return 0;
                        }
                        return o1.getCreatedDate().compareTo(o2.getCreatedDate());
                    }
                });

                return shapeList.get(shapeList.size() - 1);
            }
        }
        return null;
    }

    public void back() {
        shapeList.remove(shapeList.size() - 1);
        draw();
    }

    public void refresh() {
        shapeList.clear();
        draw();
    }

}
