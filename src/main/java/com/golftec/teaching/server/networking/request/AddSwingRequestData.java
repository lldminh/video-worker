package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.model.data.SwingMeta;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-06-09.
 */
public class AddSwingRequestData {

    @SerializedName("wuci")
    public String wuci = "";

    public String lessonId = "";
    public String swingId = "";
    public SwingMeta swingMetadata = new SwingMeta();
}
