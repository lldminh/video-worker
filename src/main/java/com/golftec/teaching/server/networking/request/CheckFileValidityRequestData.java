package com.golftec.teaching.server.networking.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-08-28.
 */
public class CheckFileValidityRequestData {

    /**
     * should contains extension (.mp4, .wav)
     */
    @SerializedName("fileName")
    public String fileName = "";

    @SerializedName("fileSize")
    public long fileSize = 0;

    @SerializedName("fileType")
    public String fileType = UploadedFileType.Swing.text;

    @SerializedName("lessonId")
    public String lessonId = "";
}
