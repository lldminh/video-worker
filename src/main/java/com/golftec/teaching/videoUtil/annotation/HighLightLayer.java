package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.golftec.teaching.videoUtil.util.GTColor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HighLightLayer {
    private Canvas canvas = null;
    private List<GTShape> shapeList = new ArrayList<GTShape>();

    public HighLightLayer(Canvas canvas){
        this.canvas = canvas;
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 2000, 2000);
        for (int i = 0; i < shapeList.size(); i++) {
            shapeList.get(i).highLight(gc);
        }
    }

    public List<GTShape> getAll(){
        return shapeList;
    }

    public void clear(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, 2000, 2000);
    }

    private boolean isExist(UUID uuid){
        for(int i = 0 ;i < shapeList.size(); i++){
            if(shapeList.get(i).id == uuid){
                return true;
            }
        }
        return false;
    }

    public void pushShape(GTShape gtShape) {
        if(!this.isExist(gtShape.id)){
            GTShape clone = gtShape.clone();
            clone.setStroke(GTColor.RED);
            clone.setLineWidth(clone.getHighLightlineWidth());
            shapeList.add(clone);
            draw();
        }
    }

    public void removeAll(){
        shapeList.clear();
        draw();
    }
}
