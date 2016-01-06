package com.golftec.video.production.data;

/**
 * Created by ThuPT on 12/31/2015.
 */
public enum ComposeStatus {
    Unknown(-1),
    Succeed(0),
    Fail(1),
    Composing(2);

    public Integer status;

    ComposeStatus(Integer status) {
        this.status = status;
    }

    public static ComposeStatus get(Integer status) {

        for (ComposeStatus composeStatus : ComposeStatus.values()) {
            if (composeStatus.status.equals(status)) {
                return composeStatus;
            }
        }
        return Unknown;
    }

}
