package com.golftec.teaching.view;

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by ThuPT on 11/10/2015.
 */
public class DrawingView {

    @SerializedName("drawingId")
    public String drawingId = "";

    @SerializedName("isSelected")
    public boolean isSelected = false;

    @SerializedName("drawingUri")
    public String drawingUri = "";

    @SerializedName("timestamp")
    public Date timestamp = DateTime.now().toDate();

    @SerializedName("imageFileSize")
    public long imageFileSize = 0;

}
