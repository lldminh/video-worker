package com.golftec.teaching.model;

import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-09-23.
 */
public class TelestrationZipMeta {

    @SerializedName("telestrationId")
    public String telestrationId;

    @SerializedName("version")
    public String version;

    @SerializedName("lessonId")
    public String lessonId;

    @SerializedName("title")
    public String title;

    @SerializedName("videoLength")
    public long videoLength;

    @SerializedName("totalFrame")
    public long totalFrame;

    /**
     * TODO: tao should change this to correct gramma
     */
    @SerializedName("isSelect")
    public boolean isSelected;

    public TelestrationZipMeta(String telestrationId, String version, String lessonId, String title, long videoLength, long totalFrame, boolean isSelected) {
        this.telestrationId = Strings.nullToEmpty(telestrationId);
        this.version = Strings.nullToEmpty(version);
        this.lessonId = Strings.nullToEmpty(lessonId);
        this.title = Strings.nullToEmpty(title);
        this.videoLength = videoLength;
        this.totalFrame = totalFrame;
        this.isSelected = isSelected;
    }
}
