package com.golftec.teaching.view;

import java.util.List;

/**
 * Created by hoang on 2015-06-08.
 */
public class ScheduleItemViewsByDay {

    public String date;
    public List<ScheduleItemView> events;

    public ScheduleItemViewsByDay(String date, List<ScheduleItemView> events) {
        this.date = date;
        this.events = events;
    }
}
