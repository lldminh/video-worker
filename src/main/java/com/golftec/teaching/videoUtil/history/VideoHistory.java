package com.golftec.teaching.videoUtil.history;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.videoUtil.control.MediaPlayerControl;
import com.golftec.teaching.videoUtil.control.MediaPlayerControlForServer;
import com.golftec.teaching.videoUtil.control.VideoStatus;
import com.golftec.teaching.videoUtil.util.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javafx.application.Platform;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * Created by Teo on 3/19/2015.
 */
public class VideoHistory implements Serializable {

    public Map<Double, VideoBoardEvent> hashMap = Maps.newHashMap();
    public Map<Double, VideoData> standByMap = Maps.newHashMap();
    public Map<Long, FrameData> videoFrameMap = Maps.newHashMap();

    transient private byte status = 0;
    transient private RecordTimer recordTimer = null;
    transient private List<Timer> timeQueue = Lists.newArrayList();

    private long videoLength = 0;

    public VideoHistory() { }

    public void pause() {
        this.status = 1;
        this.recordTimer.stop();
    }

    public void resumeHistory() {
        this.status = 0;
        this.recordTimer.resume();
    }

    public void setFilePath() {
        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            VideoBoardEvent videoBoardEvent = (VideoBoardEvent) pair.getValue();
            if (videoBoardEvent.videoButtonType == VideoButtonType.SET_PATH ||
                videoBoardEvent.videoButtonType == VideoButtonType.SET_SOURCE_URL) {

                String path = GTConstants.BASE_VIDEO_FACTORY_DIR + "/temp/" + GTUtil.uuid() + ".mp4";
                File file = new File(path);
                try {

                    URL url = new URL(videoBoardEvent.sourceURL);
//                    URL url = new URL("http://54.197.238.74:50003/pro-videos/3f2dbf42f79f119d9e6517f46b52b2b8/resized_320x480.mp4");
                    System.out.println("downloading video file: " + url);
                    FileUtils.copyURLToFile(url, file);
                } catch (Exception ex) {
                }
                videoBoardEvent.filePath = path;
            }
        }
    }



    public void setFilePathForStandBy() {
        Iterator it = standByMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            VideoData videoData = (VideoData) pair.getValue();
            if (!videoData.getSourceURL().trim().equals("")) {
                String path = GTConstants.BASE_VIDEO_FACTORY_DIR + "temp/" + GTUtil.uuid() + ".mp4";
                File file = new File(path);
                try {
                    URL url = new URL(videoData.getSourceURL());
                    System.out.println("downloading video file: " + url);
                    FileUtils.copyURLToFile(url, file);
                } catch (Exception ex) {
                }
                videoData.setFilePath(path);
            }
        }
    }

    public long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(long videoLength) {
        this.videoLength = videoLength;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getTime() {
        return this.recordTimer.getTime(new Date());
    }

    public void addStandby(VideoData videoData) {
        videoData.setTimestamp(this.getTime());
        standByMap.put(videoData.getTimestamp(), videoData);
    }

    public void cleanHistory() {
        this.hashMap.clear();
        this.standByMap.clear();
    }

    public void finish() {
        VideoData videoData = new VideoData();
        videoData.setStatus(VideoStatus.IS_FINISH);

        System.out.println("FINISH");

        videoData.setTimestamp(this.getTime());
        standByMap.put(videoData.getTimestamp(), videoData);
    }

    public void showStandbyData(MediaPlayerControl mediaPlayerControl) {
        Iterator it = standByMap.entrySet().iterator();

        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final VideoData videoData = (VideoData) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        mediaPlayerControl.consumeStandByEvent(videoData);
                    });
                }
            };

            timer.schedule(timerTask, videoData.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void showStandbyData(MediaPlayerControlForServer mediaPlayerControlForServer) {
        Iterator it = standByMap.entrySet().iterator();
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final VideoData videoData = (VideoData) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        mediaPlayerControlForServer.consumeStandByEvent(videoData);
                    });
                }
            };

            timer.schedule(timerTask, videoData.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void startRecord() {
        recordTimer = new RecordTimer();
        this.recordTimer.start();
        this.status = 0;
    }

    public void add(VideoBoardEvent videoBoardEvent) {
        if (status == 0) {
            System.out.println("hashMap total: " + hashMap.size());
            System.out.println(videoBoardEvent.videoButtonType);
            videoBoardEvent.setTimestamp(this.getTime());
            hashMap.put(videoBoardEvent.getTimestamp(), videoBoardEvent);
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

    public void serialize(String filePath, String fileName) {
        Common.serialize(filePath, fileName, this);
    }

    public void stopRecord() {
        this.videoLength = getTime();
    }

    public void showHistory(MediaPlayerControl mediaPlayerControl) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }

        showStandbyData(mediaPlayerControl);
        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final VideoBoardEvent videoBoardEvent = (VideoBoardEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (videoBoardEvent != null) {
                            mediaPlayerControl.consumeEvent(videoBoardEvent);
                        }
                    });
                }
            };

            timer.schedule(timerTask, videoBoardEvent.getTimestampRounded());
            timeQueue.add(timer);
        }
    }

    public void showHistory(MediaPlayerControlForServer mediaPlayerControlForServer) {
        if (timeQueue == null) {
            timeQueue = new ArrayList();
        }
        showStandbyData(mediaPlayerControlForServer);

        Iterator it = hashMap.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            final VideoBoardEvent videoBoardEvent = (VideoBoardEvent) pair.getValue();
            Timer timer = new Timer();

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> {
                        if (videoBoardEvent != null) {
                            mediaPlayerControlForServer.consumeEvent(videoBoardEvent);
                        }
                    });
                }
            };

            timer.schedule(timerTask, videoBoardEvent.getTimestampRounded());
            timeQueue.add(timer);
        }
    }
}
