package com.golftec.teaching.server.networking.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hoang on 2015-12-07.
 */
public class ConfirmDeleteLessonResponseData {

    @SerializedName("safeToDelete")
    private final boolean safeToDelete;

    public ConfirmDeleteLessonResponseData(boolean safeToDelete) {
        this.safeToDelete = safeToDelete;
    }
}
