package com.golftec.video.production.model;

/**
 * Created by hoang on 2015-10-13.
 */
public class StatusWorkerResponseData {

    public int code;
    public String currentCompose;

    public StatusWorkerResponseData(int code, String currentCompose) {
        this.code = code;
        this.currentCompose = currentCompose;
    }
}
