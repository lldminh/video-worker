package com.golftec.video.production.data;

/**
 * Created by ThuPT on 12/31/2015.
 */
public class ServerStatus {
    private static boolean isBusy = false;
    private static String currentCompose = "";

    public static boolean isBusy() {
        return isBusy;
    }

    public static void setBusy(String telestrationId) {
        isBusy = true;
        currentCompose = telestrationId;
    }

    public static void setFree() {
        isBusy = false;
        currentCompose = "";
    }

    public static String getCurrentCompose(){
        return currentCompose;
    }
}
