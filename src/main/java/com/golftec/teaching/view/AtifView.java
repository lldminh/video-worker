package com.golftec.teaching.view;

import com.golftec.teaching.model.lesson.Atif;

import java.io.Serializable;

/**
 * Created by hoang on 5/23/15.
 */
public class AtifView implements Serializable{

    public int A = 0;
    public int T = 0;
    public int I = 0;
    public int F = 0;

    public AtifView(Atif atif) {
        if (atif != null) {
            A = atif.getApproach();
            T = atif.getTop();
            I = atif.getImpact();
            F = atif.getFollowThrough();
        }
    }

    public AtifView() { }

    @Override
    public String toString() {
        return String.format("%d_%d_%d_%d", A, T, I, F);
    }
}
