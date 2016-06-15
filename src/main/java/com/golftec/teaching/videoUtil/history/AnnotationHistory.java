package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.annotation.AnnotationEvent;
import com.golftec.teaching.videoUtil.annotation.AnnotationPane;
import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.RecordTimer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.*;

public class AnnotationHistory implements Serializable {

    public Map<Double, AnnotationEvent> hashMap = Maps.newHashMap();
    public Map<Double, AnnotationStandByData> standByMap = Maps.newHashMap();
    public Map<Long, AnnotationStandByData> frameMap = Maps.newHashMap();

    transient private AnnotationPane annotationPane = null;
    transient private RecordTimer recordTimer = null;
    transient private byte status = 0;
    transient private List<Timer> timeQueue = Lists.newArrayList();

    transient double lastTimeStamp = 0; // this one for inorge the case: duplicating timestamp

    // 0: recording
    // 1: stop

    public AnnotationHistory(AnnotationPane annotationPane) {
        this.annotationPane = annotationPane;
    }

    public AnnotationHistory() {

    }

    public void setAnnotationPane(AnnotationPane annotationPane) {
        this.annotationPane = annotationPane;
    }

    public void cleanHistory() {
        this.hashMap.clear();
        this.standByMap.clear();
    }

    public void dispose(){
        recordTimer.stop();;
    }

    public void startRecord() {
        if(recordTimer == null){
            recordTimer = new RecordTimer();
        }

        this.recordTimer.start();
        status = 0;
    }

    public void pause() {
        this.status = 1;
        this.recordTimer.stop();
    }

    public void resumeHistory() {
        this.status = 0;
        this.recordTimer.resume();
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public void serialize(String filePath, String fileName) {
        Common.serialize(filePath, fileName, this);
    }

    public long getTime() {
        if (this.recordTimer != null) {
            return this.recordTimer.getTime(new Date());
        }
        return 0;
    }

    public void add(AnnotationEvent annotationEvent) {
        if (status == 0) {
            double timestamp = (double) this.getTime();
            if (timestamp <= lastTimeStamp) {
                timestamp = lastTimeStamp + 1;
            }
            annotationEvent.setTimestamp(timestamp);
            hashMap.put(timestamp, annotationEvent);
            lastTimeStamp = timestamp;
        }
    }

    public void addStandby(AnnotationStandByData annotationStandByData) {
        annotationStandByData.setTimestamp(this.getTime());
        standByMap.put(annotationStandByData.getTimestamp(), annotationStandByData);
    }

    public void showStandbyData() {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }
        Iterator it = standByMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final AnnotationStandByData annotationStandByData = (AnnotationStandByData) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        annotationPane.consumeStandByEvent(annotationStandByData);
                    });
                }
            };

            timer.schedule(timerTask, annotationStandByData.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    // call this method to cancel all timer has been created and init before, used to cancel showing history
    public void cancelShowHistory() {
        if (timeQueue != null) {
            for (int i = 0; i < timeQueue.size(); i++) {
                Timer timer = timeQueue.get(i);
                timer.cancel();
            }
        }
    }

    public void showHistory() {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }
        showStandbyData();

        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final AnnotationEvent annotationEvent = (AnnotationEvent) pair.getValue();

            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        annotationPane.consumeMouseEvent(annotationEvent.mouseEvent, annotationEvent.mouseEventType);
                    });
                }
            };

            timer.schedule(timerTask, annotationEvent.getTimestampRounded());
            timeQueue.add(timer);
        }
    }
}
