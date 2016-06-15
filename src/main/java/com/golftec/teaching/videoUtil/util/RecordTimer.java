package com.golftec.teaching.videoUtil.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.Date;

/**
 * Created by Teo on 3/17/2015.
 */
public class RecordTimer {

    private long time = 0; //milliseconds
    private Object lock1 = new Object();

    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            synchronized(lock1) {
                time += 3;
//                print();
            }


        }
    };
    private Timeline timeline;

    public RecordTimer() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        double duration = 3;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));
    }

    public void print(){
        System.out.println("Timer: " + time);
    }

    public void stop() {
        timeline.stop();
    }

    public long getTime(Date curTime) {
        synchronized(lock1) {
            return time;
        }
    }

    public void resume() {
        timeline.play();
    }

    public void start() {
        timeline.stop();
        synchronized(lock1) {
            time = 0;
        }
        timeline.playFromStart();
    }
}
