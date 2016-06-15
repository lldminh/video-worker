package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.model.CoachProVideoPreference;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hoang on 2015-06-26.
 */
public class UpdateProVideoPreferencesRequestData {

    public String coachId = "";

    @SerializedName("proVideoPreferences")
    public List<CoachProVideoPreference> coachProVideoPreferences = Lists.newArrayList();
}
