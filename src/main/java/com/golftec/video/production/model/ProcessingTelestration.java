package com.golftec.video.production.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ThuPT on 12/28/2015.
 */
public class ProcessingTelestration {

    private static Set<String> telestrationSet;

    private ProcessingTelestration() {
    }

    ;

    public static Set<String> instance() {
        if (telestrationSet == null) {
            telestrationSet = new HashSet<>();
        }
        return telestrationSet;
    }
}
