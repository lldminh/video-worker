package com.golftec.teaching.server.networking.request;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by hoang on 2015-09-30.
 */
public class SaveTelestrationMetaRequestData {

    public String lessonId = "";
    public String telestrationId = "";
    public String version = "";
    public Date createdDate = DateTime.now().toDate();
    public String title = "";
    public String content = "";
    /**
     * Video-Length in milliseconds
     */
    public long videoLength = 0;
    public boolean isSelected = false;
}
