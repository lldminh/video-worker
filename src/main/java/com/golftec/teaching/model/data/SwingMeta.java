package com.golftec.teaching.model.data;

import com.golftec.teaching.model.*;

import java.io.Serializable;

public class SwingMeta implements Serializable {

    public DominantHand dominantHand = DominantHand.Right;
    public Sex sex = Sex.Male;
    public long length = 0;
    public SwingType swingType = SwingType.Putt;
    public Orientation orientation = Orientation.Front;
    public Order order = Order.Before;

    public SwingMeta(DominantHand dominantHand, Sex sex, int length, SwingType swingType, Orientation orientation, Order order) {
        this.dominantHand = dominantHand;
        this.sex = sex;
        this.length = length;
        this.swingType = swingType;
        this.orientation = orientation;
        this.order = order;
    }

    public SwingMeta() { }

    @Override
    public String toString() {
        return String.format("%s_%s_%d_%s_%s_%s", dominantHand, sex, length, swingType, orientation, order);
    }
}
