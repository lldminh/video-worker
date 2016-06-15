package com.golftec.teaching.model.types;

import java.io.Serializable;

public enum EnvironmentType implements Serializable {

    UNKNOWN(""),
    GolftecToGo("GolfTEC To Go"),
    MyProToGo("My Pro To Go"),
    GolftecEvent("GolfTEC Event"),
    GolftecOutdoors("GolfTEC Outdoors");

    public String displayName;

    EnvironmentType(String displayName) {
        this.displayName = displayName;
    }

    public static EnvironmentType safeValueOf(String environment) {
        try {
            return EnvironmentType.valueOf(environment);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return UNKNOWN;
    }
}
