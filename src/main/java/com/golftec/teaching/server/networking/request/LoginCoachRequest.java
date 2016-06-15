package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.server.networking.type.GTMethod;

/**
 * Created by Hoang on 2015-04-06.
 */
@Deprecated()
public class LoginCoachRequest extends Request {

    public LoginCoachRequest(LoginRequestData loginRequestData, String installationId) {
        super(GTMethod.Login.id, loginRequestData, installationId, GTUtil.uuid());
    }
}
