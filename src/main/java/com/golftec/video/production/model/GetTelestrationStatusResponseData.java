package com.golftec.video.production.model;

/**
 * Created by ThuPT on 1/4/2016.
 */
public class GetTelestrationStatusResponseData {
    public int code;
    public String message;
    public String url;

    public GetTelestrationStatusResponseData(int code, String message, String url) {
        this.code = code;
        this.message = message;
        this.url = url;
    }
}
