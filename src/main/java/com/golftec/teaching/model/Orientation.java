package com.golftec.teaching.model;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public enum Orientation implements Serializable {

    Front("Front", "F"),
    Side("Side", "S");

    List<String> names = Lists.newArrayList();

    Orientation(String... names) {
        this.names = Arrays.asList(names);
    }

    public static Orientation fromName(String name) {
        if (Strings.isNullOrEmpty(name)) {
            return Front;
        }

        for (Orientation orientation : values()) {
            if (orientation.names.contains(name)) {
                return orientation;
            }
        }

        return Front;
    }
}
