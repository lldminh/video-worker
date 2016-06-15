package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.Store;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * This model has the coach specific info.
 *
 * @author Al Wells
 */
public class Coach implements Serializable {

    @SerializedName("coachId")
    public String id = "";
    public String name = "";
    public String token = "";
    public String avatarUrl = "";
    /**
     * TODO: changed to Date
     */
    public long loginTime = 0;
    public long logoutTime = 0;
    public String ipAddress = "";
    public String sessionId = "";

    /**
     * login succeed/fail is a per session status, should not be a property of the coach himself
     */
    @Deprecated
    public boolean loginSucceeded = false;

    @Deprecated
    public String loginFailureReason = "";
    public String username = "";
    public String password;

    /**
     * This is the deviceId of the coach of whatever device he is logged in on.
     */
    public String currentDeviceId;

    @SerializedName("stores")
    public List<Store> worksAt = Lists.newArrayList();

    @SerializedName("pgaStatus")
    public String pgaStatus = "";

    @Deprecated
    public String getName() {
        return name;
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
    }
}
