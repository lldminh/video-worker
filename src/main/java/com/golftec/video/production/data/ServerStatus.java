package com.golftec.video.production.data;

/**
 * Created by ThuPT on 12/31/2015.
 */
public class ServerStatus {
    private static Boolean isBusy;

    private ServerStatus() {
    }


    public static Boolean get() {
        if (isBusy == null) {
            isBusy = new Boolean(false);
        }
        return isBusy;
    }
}
