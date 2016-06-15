package com.golftec.teaching.videoUtil.motion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sonleduc on 3/5/15.
 */
public class MotionDataSet {
    private HashMap hashMap = null;

    public MotionDataSet(){
        hashMap = new HashMap();
    }

    public List<MotionData> get(int frame) {
        return (List<MotionData>)hashMap.get(frame);
    }

    public void add(MotionData motionData) {
        List<MotionData> tmp = (List<MotionData>) hashMap.get(motionData.getFrame());
        if (tmp == null) {
            tmp = new ArrayList<MotionData>();
            tmp.add(motionData);
            hashMap.put(motionData.getFrame(), tmp);
        } else {
            tmp.add(motionData);
        }
    }
}
