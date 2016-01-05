package com.golftec.video.production.data;

/**
 * Created by ThuPT on 12/31/2015.
 */
public class ServerStatus {
    private static boolean isBusy = false;

    public static boolean isBusy() {
        return isBusy;
    }

    public static void setBusy() {
        isBusy = true;
    }

    public static void setFree() {
        isBusy = false;
    }
}
