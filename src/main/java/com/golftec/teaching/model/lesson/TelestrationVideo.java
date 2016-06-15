package com.golftec.teaching.model.lesson;

import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.MotionHistory;
import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TelestrationVideo implements Serializable {

    /**
     * different version will use different code to parse/compose video, and also this field is used for tracing error on Shipped production code
     */
    @SerializedName("version")
    public String telestrationVersion;

    private String lessonId;
    /**
     * TODO: verify why there is wuci here?
     */
    @Deprecated
    private String wuci;
    private String telestrationId;
    private AnnotationHistory leftAnnotationHistory;
    private AnnotationHistory rightAnnotationHistory;
    private MotionHistory leftMotionHistory;
    private MotionHistory rightMotionHistory;
    private ToolBoardHistory toolBoardHistory;
    private VideoHistory leftVideoHistory;
    private VideoHistory rightVideoHistory;
    private BufferedImage telestrationImage = null;
    private byte[] soundBytes = null;
    private List<String> devicesAddedTo = null;
    private long videoLength;

    /**
     * local path to server's .wav file for this telestration.
     */
    private String soundFilePath;

    public String getTelestrationId() {
        return telestrationId;
    }

    public void setTelestrationId(String telestrationId) {
        this.telestrationId = telestrationId;
    }

    @Deprecated
    public String getWuci() {
        return wuci;
    }

    @Deprecated
    public void setWuci(String wuci) {
        this.wuci = wuci;
    }

    public AnnotationHistory getLeftAnnotationHistory() {
        return leftAnnotationHistory;
    }

    public void setLeftAnnotationHistory(AnnotationHistory leftAnnotationHistory) {
        this.leftAnnotationHistory = leftAnnotationHistory;
    }

    public AnnotationHistory getRightAnnotationHistory() {
        return rightAnnotationHistory;
    }

    public void setRightAnnotationHistory(AnnotationHistory rightAnnotationHistory) {
        this.rightAnnotationHistory = rightAnnotationHistory;
    }

    public MotionHistory getLeftMotionHistory() {
        return leftMotionHistory;
    }

    public void setLeftMotionHistory(MotionHistory leftMotionHistory) {
        this.leftMotionHistory = leftMotionHistory;
    }

    public MotionHistory getRightMotionHistory() {
        return rightMotionHistory;
    }

    public void setRightMotionHistory(MotionHistory rightMotionHistory) {
        this.rightMotionHistory = rightMotionHistory;
    }

    public ToolBoardHistory getToolBoardHistory() {
        return toolBoardHistory;
    }

    public void setToolBoardHistory(ToolBoardHistory toolBoardHistory) {
        this.toolBoardHistory = toolBoardHistory;
    }

    public VideoHistory getLeftVideoHistory() {
        return leftVideoHistory;
    }

    public void setLeftVideoHistory(VideoHistory leftVideoHistory) {
        this.leftVideoHistory = leftVideoHistory;
    }

    public BufferedImage getTelestrationImage() {
        return telestrationImage;
    }

    public void setTelestrationImage(BufferedImage telestrationImage) {
        this.telestrationImage = telestrationImage;
    }

    public VideoHistory getRightVideoHistory() {
        return rightVideoHistory;
    }

    public void setRightVideoHistory(VideoHistory rightVideoHistory) {
        this.rightVideoHistory = rightVideoHistory;
    }

    public byte[] getSoundBytes() {
        return soundBytes;
    }

    public void setSoundBytes(byte[] soundBytes) {
        this.soundBytes = soundBytes;
    }

    public void addToDevice(String deviceId) {
        if (devicesAddedTo == null) {
            devicesAddedTo = new ArrayList<String>();
        }
        devicesAddedTo.add(deviceId);
    }

    public List<String> getVideoURLs() {
        try {
            final List<String> urls = Lists.newArrayList();
            if (leftVideoHistory != null && leftVideoHistory.videoFrameMap != null) {
                final List<String> leftDistinctLinks = leftVideoHistory.videoFrameMap.values().stream()
                                                                                     .filter(fd -> fd != null && !Strings.isNullOrEmpty(fd.sourceURL))
                                                                                     .map(fd -> fd.sourceURL)
                                                                                     .distinct()
                                                                                     .collect(toList());
                urls.addAll(leftDistinctLinks);
            }
            if (rightVideoHistory != null && rightVideoHistory.videoFrameMap != null) {
                final List<String> rightDistinctLinks = rightVideoHistory.videoFrameMap.values().stream()
                                                                                       .filter(fd -> fd != null && !Strings.isNullOrEmpty(fd.sourceURL))
                                                                                       .map(fd -> fd.sourceURL)
                                                                                       .distinct()
                                                                                       .collect(toList());
                urls.addAll(rightDistinctLinks);
            }
            return urls.stream()
                       .distinct()
                       .collect(toList());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Lists.newArrayList();
    }

    public long getTotalFrameOfVideo() {
        try {
            long leftAnnotationFrames = 0;
            long rightAnnotationFrames = 0;
            long leftVideoFrames = 0;
            long rightVideoFrames = 0;

            if (this.leftAnnotationHistory != null) {
                if (this.leftAnnotationHistory.frameMap != null) {
                    leftAnnotationFrames = this.leftAnnotationHistory.frameMap.size();
                }
            }

            if (this.rightAnnotationHistory != null) {
                if (this.rightAnnotationHistory.frameMap != null) {
                    rightAnnotationFrames = this.rightAnnotationHistory.frameMap.size();
                }
            }

            if (this.leftVideoHistory != null) {
                if (this.leftVideoHistory.videoFrameMap != null) {
                    leftVideoFrames = this.leftVideoHistory.videoFrameMap.size();
                }
            }

            if (this.rightVideoHistory != null) {
                if (this.rightVideoHistory.videoFrameMap != null) {
                    rightVideoFrames = this.rightVideoHistory.videoFrameMap.size();
                }
            }

            List list = new ArrayList();
            list.add(leftAnnotationFrames);
            list.add(rightAnnotationFrames);
            list.add(leftVideoFrames);
            list.add(rightVideoFrames);
            return (long) Collections.max(list);
        } catch (Exception ex) {
            System.out.println("Cannot get total frames of video");
            return 0;
        }
    }

    public List<String> getDevicesTelestrationIsOn() {
        return devicesAddedTo;
    }

    public long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(long time) {
        this.videoLength = time;
    }

    public String getSoundFilePath() {
        return soundFilePath;
    }

    public void setSoundFilePath(String soundFilePath) {
        this.soundFilePath = soundFilePath;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }
}
