package com.golftec.teaching.model;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * This model hold most metadata of a telestration, everything except the actual telestration-json that is parsed when composing video.
 * The purpose of this is to be able to access cheaply those data without having to parse the whole telestration-json.
 * When there's a database, this will be map to the telestration table with added json field that whole those full json (of TelestrationVideo class)
 * <p>
 * Created by hoang on 2015-08-19.
 */
public class Telestration {

    /**
     * used in lesson-finish tab, for sorting
     */
    public Date timestamp = DateTime.now().toDate();

    /**
     * different version will use different code to parse/compose video, and also this field is used for tracing error on Shipped production code
     */
    @SerializedName("telestrationVersion")
    public String telestrationVersion = "";

    @SerializedName("lessonId")
    public String lessonId = "";

    @SerializedName("telestrationId")
    public String telestrationId = "";

    /**
     * Video-Length in milliseconds
     */
    @SerializedName("videoLength")
    public long videoLength;

    @SerializedName("title")
    public String title = "";

    @SerializedName("content")
    public String content = "";

    @SerializedName("wavURL")
    public String wavURL = "";

    @SerializedName("jsonURL")
    public String jsonURL = "";

    /**
     * TODO: add a new flow to upload this thumbnail for desktop.
     * For IPad, although there's thumbnail image in the zip, we might use it for now.
     * However we SHOULD NOT RELY on it. Material to compose the final video (json) should not stay in the same .zip with one of the result (thumbnail).
     * Should make an explicit function to upload the thumbnail, similar to swing-thumbnail
     */
    @SerializedName("thumbnailURL")
    public String thumbnailURL = "";

    @SerializedName("zipURL")
    public String zipURL = "";

    /**
     * .wav file size on disk (not the length of the audio)
     */
    @SerializedName("wavFileSize")
    public long wavFileSize = 0;

    /**
     * telestration json file size on disk.
     */
    @SerializedName("jsonFileSize")
    public long jsonFileSize = 0;

    @SerializedName("zipFileSize")
    public long zipFileSize = 0;

    @SerializedName("wavFileSaved")
    public boolean wavFileSaved;

    @SerializedName("jsonFileSaved")
    public boolean jsonFileSaved;

    @SerializedName("zipFileSaved")
    public boolean zipFileSaved;

    /**
     * So this field track the number of times server has tried to compose video for this telestration.
     * We should not run compose-video more than a pre-configured number of times. The config is, as normal, in GTServerConstant.Option
     */
    @SerializedName("processCount")
    public int processCount = 0;

    /**
     * boolean flag indicate that the video has been compose successfully for this telestration
     */
    @SerializedName("videoComposed")
    public boolean videoComposed;

    /**
     * boolean flag indicate that the video is being composed by a worker
     */
    @SerializedName("videoComposing")
    public boolean videoComposing;

    /**
     * This field is intended to be used to mark a telestration that is selected (in `Finish Lesson` tab) or not.
     * TODO: migrate the lessonView.finishedTelestrations list to lessonView.telestrations, using this field.
     * TODO: probably do the migration at server start-up, for each finishedTelestrations, mark the corresponding telestrations item with `isSelected = true`
     */
    @SerializedName("isSelected")
    public boolean isSelected;

    /**
     * Save last status of telestration after call job compose
     */
    @SerializedName("lastStatus")
    public String lastStatus="";

    @SerializedName("lastRequireResubmitOn")
    public String lastRequireResubmitBy = "";
}
