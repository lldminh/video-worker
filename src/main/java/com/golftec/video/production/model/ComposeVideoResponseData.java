package com.golftec.video.production.model;

/**
 * Created by hoang on 2015-10-13.
 */
public class ComposeVideoResponseData {

    public int code;
    public String message;
    public String currentCompose;

    public ComposeVideoResponseData(int code, String message, String currentCompose) {
        this.code = code;
        this.message = message;
        this.currentCompose = currentCompose;
    }
}
