package com.golftec.teaching.model.data;

import com.golftec.teaching.model.lesson.Atif;
import com.golftec.teaching.model.lesson.MotionData;
import com.golftec.teaching.model.preferences.Preference;
import com.golftec.teaching.model.preferences.PreferenceType;
import com.golftec.teaching.model.types.MotionDataType;

import java.util.List;
import java.util.Map;

public class ProVideo implements Preference {

    private String id;
    private long dateAdded;
    private boolean isLeft;
    private boolean isRight;
    private boolean isFront;
    private boolean isSide;
    private Atif atif;
    private Map<MotionDataType, List<MotionData>> motionDataMap;
    private String proName;
    private String videoUri;
    private String thumbnailUri;

    private boolean isPreferred;
    private boolean isHidden;

    private SwingMeta swingMetadata;

    @Override
    public PreferenceType getPreferenceType() {
        return PreferenceType.ProVideo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean isLeft) {
        this.isLeft = isLeft;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean isRight) {
        this.isRight = isRight;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean isFront) {
        this.isFront = isFront;
    }

    public boolean isSide() {
        return isSide;
    }

    public void setSide(boolean isSide) {
        this.isSide = isSide;
    }

    public Atif getAtif() {
        return atif;
    }

    public void setAtif(Atif atif) {
        this.atif = atif;
    }

    public Map<MotionDataType, List<MotionData>> getMotion() {
        return motionDataMap;
    }

    public void setMotion(Map<MotionDataType, List<MotionData>> motionDataMap) {
        this.motionDataMap = motionDataMap;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    @Override
    public void setPreferred(boolean preferred) {
        this.isPreferred = preferred;
    }

    @Override
    public Boolean isPreferred() {
        return isPreferred;
    }

    @Override
    public void setHidden(boolean hidden) {
        this.isHidden = hidden;
    }

    @Override
    public Boolean isHidden() {
        return isHidden;
    }

    public SwingMeta getSwingMeta() {
        return swingMetadata;
    }

    public void setSwingMeta(SwingMeta swingMetadata) {
        this.swingMetadata = swingMetadata;
    }

    public Map<MotionDataType, List<MotionData>> getMotionDataMap() {
        return motionDataMap;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }
}
