package com.golftec.teaching.server.networking.request;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * TODO: Check this: Should merge with the main telestration data class.
 * <p>
 * Created by hoang on 2015-08-10.
 */
public class FinishedTelestration {

    public String id = "";
    public String title = "";
    public String description = "";
    public Date created = DateTime.now().toDate();
    public long videoLength;
}
