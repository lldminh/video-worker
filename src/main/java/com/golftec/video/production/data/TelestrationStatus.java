package com.golftec.video.production.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class TelestrationStatus {

    private static Map<String, Integer> telestrationStatusMap;

    private TelestrationStatus() {
    }


    public static Map<String, Integer> get() {
        if (telestrationStatusMap == null) {
            telestrationStatusMap = new HashMap<>();
        }
        return telestrationStatusMap;
    }
}
