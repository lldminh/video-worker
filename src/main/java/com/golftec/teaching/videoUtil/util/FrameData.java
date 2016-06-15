package com.golftec.teaching.videoUtil.util;

import java.io.Serializable;

/**
 * Created by Teo on 2015-07-10.
 */
public class FrameData implements Serializable {
    public long srcFrame = 0;
    public String sourceURL = "";
    public String filePath = "";
    public long desFrame = 0;
    public boolean isFlip = false;
}
