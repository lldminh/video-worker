package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.types.ATIFType;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.Map;

/**
 * The ATIF model for any given swing. You need a swing for an ATIF
 *
 * @author Al Wells
 */
public class Atif implements Serializable {

    private Map<ATIFType, Integer> atifMap = Maps.newConcurrentMap();

    public void setAtifInfo(ATIFType atifType, int frameNumber) {
        atifMap.put(atifType, frameNumber);
    }

    public int getApproach() {
        return atifMap.get(ATIFType.A);
    }

    public int getTop() {
        return atifMap.get(ATIFType.T);
    }

    public int getImpact() {
        return atifMap.get(ATIFType.I);
    }

    public int getFollowThrough() {
        return atifMap.get(ATIFType.F);
    }
}
