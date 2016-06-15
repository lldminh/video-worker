package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.videoUtil.annotation.ToolBoard;
import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.RecordTimer;
import com.golftec.teaching.videoUtil.util.ToolBoardEvent;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.application.Platform;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Teo on 3/17/2015.
 */
public class ToolBoardHistory implements Serializable {

    public Map<Double, ToolBoardEvent> hashMap = Maps.newHashMap();

    public Map<Double, ToolBoardEvent> standByMap = Maps.newHashMap();

    transient private ToolBoard toolBoard = null;
    private boolean isRunning = false;
    transient private RecordTimer recordTimer = null;
    transient private List<Timer> timeQueue = Lists.newArrayList();
    transient private byte status = 0;

    public ToolBoardHistory(ToolBoard toolBoard) {
        this.toolBoard = toolBoard;
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

    public void showStandbyData() {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        Iterator it = standByMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final ToolBoardEvent toolBoardEvent = (ToolBoardEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        toolBoard.consumeMouseEvent(toolBoardEvent.toolType, null);
                    });
                }
            };

            timer.schedule(timerTask, toolBoardEvent.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void addStandby(ToolBoardEvent toolBoardEvent) {
        toolBoardEvent.setTimestamp(this.getTime());
        standByMap.put(toolBoardEvent.getTimestamp(), toolBoardEvent);
    }

    public void setToolBoard(ToolBoard toolBoard) {
        this.toolBoard = toolBoard;
    }

    public void add(ToolBoardEvent toolBoardEvent) {
        if (!isRunning) {
            toolBoardEvent.setTimestamp(this.getTime());
            hashMap.put(toolBoardEvent.getTimestamp(), toolBoardEvent);
        }
    }

    public void serialize(String filePath, String fileName) {
        Common.serialize(filePath, fileName, this);
    }

    public void startRecord() {
        recordTimer = new RecordTimer();
        this.recordTimer.start();
        status = 0;
    }

    public void cleanHistory() {
        this.hashMap.clear();
        this.standByMap.clear();
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

    public long getTime() {
        return this.recordTimer.getTime(new Date());
    }

    public void showHistory() {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        showStandbyData();

        isRunning = true;

        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final ToolBoardEvent toolBoardEvent = (ToolBoardEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (toolBoardEvent != null) {
                            toolBoard.consumeMouseEvent(toolBoardEvent.toolType, null);
                        }
                    });
                }
            };

            timer.schedule(timerTask, toolBoardEvent.getTimestampRounded());
            timeQueue.add(timer);
        }
    }
}
