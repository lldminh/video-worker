package com.golftec.teaching.server.networking.request;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by ThuPT on 11/10/2015.
 */
public class AddDrawingRequestData {

    public String lessonId = "";
    public String drawingId = "";
    public boolean isSelected = false;
    public Date timestamp = DateTime.now().toDate();
}
