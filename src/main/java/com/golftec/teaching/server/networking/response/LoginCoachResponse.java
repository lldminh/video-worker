package com.golftec.teaching.server.networking.response;

import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.server.networking.type.GTMethod;

@Deprecated()
public class LoginCoachResponse extends Response {

    public LoginCoachResponse(Coach coach, String requestId, int responseCode, String errorMessage) {
        super(responseCode, errorMessage, coach, GTMethod.Login.id, requestId);
    }
}
