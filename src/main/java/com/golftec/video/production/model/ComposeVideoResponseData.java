package com.golftec.video.production.model;

/**
 * Created by hoang on 2015-10-13.
 */
public class ComposeVideoResponseData {

    public int status;
    public String message;

    public ComposeVideoResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
