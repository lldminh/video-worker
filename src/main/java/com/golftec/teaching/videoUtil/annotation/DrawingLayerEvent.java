package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.MouseEventType;

import java.io.Serializable;

/**
 * Created by Teo on 3/17/2015.
 */
public class DrawingLayerEvent implements Serializable {
    public GTMouseEvent mouseEvent = null;
    public long timestamp = 0;
    public MouseEventType mouseEventType = null;
}
