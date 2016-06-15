package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.annotation.AnnotationPane;
import com.golftec.teaching.videoUtil.annotation.DrawingLayerEvent;
import com.golftec.teaching.videoUtil.util.RecordTimer;
import javafx.application.Platform;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Teo on 3/17/2015.
 */
public class DrawingLayerHistory implements Serializable{
    private Map hashMap = null;
    transient private AnnotationPane annotationPane = null;
    private boolean isRunning = false;
    private RecordTimer recordTimer = null;

    public List<DrawingLayerEvent> get(long timestamp) {
        return (List<DrawingLayerEvent>) hashMap.get(timestamp);
    }

    public DrawingLayerHistory(AnnotationPane annotationPane) {
        hashMap = new ConcurrentHashMap();
        this.annotationPane = annotationPane;
    }

    public void setAnnotationPane(AnnotationPane annotationPane){
        this.annotationPane = annotationPane;
    }

    public void startRecord(){
        recordTimer = new RecordTimer();
        this.recordTimer.start();
    }

    public long getTime(){
        return this.recordTimer.getTime(new Date());
    }


    public void add(DrawingLayerEvent drawingLayerEvent) {
        if (!isRunning) {
            drawingLayerEvent.timestamp = this.getTime();
            hashMap.put(drawingLayerEvent.timestamp, drawingLayerEvent);
        }
    }

    public void showHistory() {
        isRunning = true;
        Iterator it = hashMap.entrySet().iterator();


        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final DrawingLayerEvent drawingLayerEvent = (DrawingLayerEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        annotationPane.consumeMouseEvent(drawingLayerEvent.mouseEvent, drawingLayerEvent.mouseEventType);
                    });
                }
            };

            timer.schedule(timerTask, drawingLayerEvent.timestamp);
        }


//        isRunning = false;
    }
}
