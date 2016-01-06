package com.golftec.video.production.model;

/**
 * Created by ThuPT on 1/6/2016.
 */
public class DeleteTelestrationOutputResponseData {
    public int code;
    public String errorMessage;

    public DeleteTelestrationOutputResponseData(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
