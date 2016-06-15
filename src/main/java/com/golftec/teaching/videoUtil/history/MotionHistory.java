package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.motion.MotionDataBoard;
import com.golftec.teaching.videoUtil.motion.MotionDataBoardForServer;
import com.golftec.teaching.videoUtil.motion.MotionEvent;
import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.RecordTimer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.*;

/**
 * Created by sonleduc on 3/24/15.
 */
public class MotionHistory implements Serializable {

    public Map<Double, MotionEvent> hashMap = Maps.newHashMap();
    public Map<Double, MotionStandByData> standByMap = Maps.newHashMap();

    transient private RecordTimer recordTimer = null;
    transient private byte status = 0;
    transient private List<Timer> timeQueue = Lists.newArrayList();

    public MotionHistory() { }

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

    public void add(MotionEvent motionEvent) {
        if (status == 0) {
            motionEvent.setTimestamp(this.getTime());
            hashMap.put(motionEvent.getTimestamp(), motionEvent);
        }
    }

    public void serialize(String filePath, String fileName) {
        Common.serialize(filePath, fileName, this);
    }

    public void startRecord() {
        recordTimer = new RecordTimer();
        this.recordTimer.start();
        status= 0;
    }

    public void showStandbyData(MotionDataBoard motionDataBoard) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        Iterator it = standByMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final MotionStandByData motionStandByData = (MotionStandByData) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        motionDataBoard.consumeStandByEvent(motionStandByData);
                    });
                }
            };

            timer.schedule(timerTask, motionStandByData.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void cleanHistory() {
        this.hashMap.clear();
        this.standByMap.clear();
    }

    public void showStandbyData(MotionDataBoardForServer motionDataBoardForServer) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        Iterator it = standByMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final MotionStandByData motionStandByData = (MotionStandByData) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        motionDataBoardForServer.consumeStandByEvent(motionStandByData);
                    });
                }
            };

            timer.schedule(timerTask, motionStandByData.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void addStandby(MotionStandByData motionStandByData) {
        motionStandByData.setTimestamp(this.getTime());
        standByMap.put(motionStandByData.getTimestamp(), motionStandByData);
    }

    public long getTime() {
        return this.recordTimer.getTime(new Date());
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

    public void showHistory(MotionDataBoard motionDataBoard) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        showStandbyData(motionDataBoard);

        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final MotionEvent motionEvent = (MotionEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (motionEvent != null) {
                            motionDataBoard.consumeEvent(motionEvent);
                        }
                    });
                }
            };

            timer.schedule(timerTask, motionEvent.getTimestampRounded());
            timeQueue.add(timer);
        }

//        isRunning = false;
    }

    public void showHistory(MotionDataBoardForServer motionDataBoardForServer) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        showStandbyData(motionDataBoardForServer);

        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final MotionEvent motionEvent = (MotionEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (motionEvent != null) {
                            motionDataBoardForServer.consumeEvent(motionEvent);
                        }
                    });
                }
            };

            timer.schedule(timerTask, motionEvent.getTimestampRounded());
            timeQueue.add(timer);
        }

//        isRunning = false;
    }
}
