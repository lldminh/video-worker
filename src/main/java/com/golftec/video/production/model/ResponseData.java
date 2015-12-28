package com.golftec.video.production.model;

/**
 * Created by hoang on 2015-10-13.
 */
public class ResponseData {

    public int status;
    public String message;

    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
