package com.golftec.teaching.server.networking.request;

import com.golftec.teaching.model.LessonType;
import com.golftec.teaching.model.data.CoachNote;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by hoang on 2015-09-29.
 */
public class SaveLessonRequestData {

    public String lessonId = "";
    public String wuci = "";
    public String coachId = "";
    public String storeId = "";
    public LessonType lessonType = LessonType.Short30;
    public int lessonStateId = 0;
    public CoachNote coachNote = new CoachNote();
    public List<String> selectedTecCards = Lists.newArrayList();
    public List<String> selectedDrills = Lists.newArrayList();
    @Deprecated
    public List<String> telestrationIds = Lists.newArrayList();
    public List<String> selectedTelestrationIds = Lists.newArrayList();
    /**
     * total telestration video length in milliseconds
     */
    public long totalVideoLength = 0;
    public Date startedDateTime = DateTime.now().toDate(); //TODO: check with Tao to see if this should be null or initialized with DateTime.now()
    public Date lastStatusDateTime = DateTime.now().toDate();
}
