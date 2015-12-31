package com.golftec.video.production.data;

/**
 * Created by ThuPT on 12/31/2015.
 */
public class ServerStatus {
    private static Boolean isFree;

    private ServerStatus() {
    }


    public static Boolean instance() {
        if (isFree == null) {
            isFree = new Boolean(false);
        }
        return isFree;
    }
}
