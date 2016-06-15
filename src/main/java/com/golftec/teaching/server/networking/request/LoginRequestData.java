package com.golftec.teaching.server.networking.request;

import java.io.Serializable;

/**
 * Created by Hoang on 2015-04-06.
 */
public class LoginRequestData implements Serializable {

    public final String username;
    public final String password;

    public LoginRequestData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
