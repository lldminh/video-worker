package com.golftec.teaching.model.lesson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-05-28.
 */
public class Environment {

    @SerializedName("environmentId")
    public String id;
    @SerializedName("environmentName")
    public String name;

    public Environment(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
