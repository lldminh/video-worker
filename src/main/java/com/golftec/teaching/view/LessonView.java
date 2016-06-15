package com.golftec.teaching.view;

import com.golftec.teaching.common.JsonExcluded;
import com.golftec.teaching.model.LessonState;
import com.golftec.teaching.model.LessonType;
import com.golftec.teaching.model.SwitchState;
import com.golftec.teaching.model.Telestration;
import com.golftec.teaching.model.data.CoachNote;
import com.golftec.teaching.model.lesson.Lesson;
import com.golftec.teaching.model.lesson.Student;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.model.types.EnvironmentType;
import com.golftec.teaching.server.networking.request.FinishedTelestration;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Created by hoang on 5/22/15.
 */
public class LessonView implements Serializable {

    /**
     * The version of the system that is used to created this lesson, basically just IPad version for now
     */
    @SerializedName("version")
    public String version;

    @SerializedName("lessonId")
    public String id = "";

    @SerializedName("wuci")
    public String wuci = "";
    public String environmentTypeName = EnvironmentType.GolftecToGo.displayName;
    public String script = "";

    @SerializedName("swings")
    public List<SwingView> swings = Lists.newArrayList();

    @SerializedName("drawings")
    public List<DrawingView> drawings = Lists.newArrayList();

    /**
     * DO NOT USE THIS, use the telestrations/finishedTelestrations instead
     */
    @Deprecated
    @JsonExcluded
    public List<TelestrationVideo> telestrationVideos = Lists.newArrayList();
    /**
     * TODO: to make it more consistent (and more flexible in model design), should only keep the studentId here, or otherwise, should also keep the Coach object in this lessonView
     */
    public Student student = new Student();

    @SerializedName("date")
    public Date lessonDate = DateTime.now().toDate();
    public CoachNote coachNote = new CoachNote();
    @SerializedName("drills")
    public List<String> drillIds = Lists.newArrayList();
    public List<String> tecCardIds = Lists.newArrayList();
    public String lessonType = LessonType.Short30.name;
    /**
     * This is deprecated but still kept for a while to reload and mapped telestration to the new finishedTelestrations (telestrations info that is put to server in FinishLesson flow)
     */
    @Deprecated
    public List<String> finalTelestrationIds = Lists.newArrayList();

    @SerializedName("telestrations")
    public List<Telestration> telestrations = Lists.newArrayList();

    /**
     * TODO:
     * Should merge with the main telestrations list, with isSelected flag in each telestration.
     * In order to do so, in FinishLesson flow, instead of filling this list, mark the selected one in main `telestrations` list.
     */
    public List<FinishedTelestration> finishedTelestrations = Lists.newArrayList();

    public Date modified = DateTime.now().toDate();
    public boolean isTelestrationComposed;
    public String coachId;
    public DateTime started = DateTime.now();
    /**
     * TODO: refactor this: currently this is to mark if a lesson is submitted to PHP endpoint or not.
     * We should use another way (configurable retries, for example)
     */
    public boolean isSubmittedOk;
    /**
     * obtained from lesson_submit flow
     */
    public String workflowId;
    public String s3Bucket;
    public String s3UploadPath;
    /**
     * mark the succeed of uploading to S3 after lesson_submit called
     */
    public boolean isUploadedToS3Ok;
    /**
     * where this lesson took place, it is important because this is how coach get paid
     */
    public String storeId;
    /**
     * Datetime of the last change of lessonState
     */
    @SerializedName("lastStatusDateTime")
    public Date lastStatusDateTime = DateTime.now().toDate();
    /**
     * total telestration video length in milliseconds
     */
    public long totalVideoLength;
    /**
     * this count the total of time this lesson has been post-process (telestrations + upload S3 + submitted to Golftec), if it exceed GTServerConstant.Option.MaxSubmitCount, then lesson will not be submitted anymore.
     * TODO: Instead, it should be changed to another state
     */
    @SerializedName("postProcessCount")
    public int postProcessCount = 0;
    public String resubmittedBy = "";
    //save list state of lessons move between done-archived and lesson-data
    public List<SwitchState> switchStates = Lists.newArrayList();
    /**
     * The main field for keeping track of status of lesson. Should use this in stead of the other boolean field.
     */
    @SerializedName("lessonState")
    private LessonState lessonState = LessonState.NotSet;
    /**
     * The int field that is used to talk with IPad client, map to the value of the lessonState field
     */
    @SerializedName("lessonStatus")
    public int lessonStatus = getLessonState().intValue;
    private EnvironmentType environmentType = EnvironmentType.GolftecToGo;

    public LessonView(Lesson lesson) {
        id = Strings.nullToEmpty(lesson.getWorkflowId());
        setEnvironmentType(lesson.getEnvironmentType());
        script = Strings.nullToEmpty(lesson.getScript());
        if (lesson.getSwings() != null && lesson.getSwings().size() > 0) {
            swings = lesson.getSwings().stream()
                           .map(SwingView::new)
                           .collect(toList());
        }
        if (lesson.getStudent() != null) {
            student = lesson.getStudent();
        }
    }

    public LessonView() {}

    public LessonView(String id, String lessonType, EnvironmentType environmentType, Student student, List<SwingView> swings, String script) {
        this.id = id;
        this.setEnvironmentType(environmentType);
        this.script = script;
        this.student = student;
        this.swings = swings;
        this.lessonType = lessonType;
        this.setLessonState(LessonState.NotSet);
    }

    /**
     * just for the lambda reverse comparison
     */
    public Date lastModified() {
        return modified;
    }

    @Override
    public String toString() {
        return this.id;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
        this.environmentTypeName = environmentType.displayName;
    }

    /**
     * The main field to determine status of a lesson
     */
    public LessonState getLessonState() {
        return lessonState;
    }

    public void setLessonState(LessonState lessonState) {
        this.lessonState = lessonState;
        this.lessonStatus = lessonState.intValue;
        this.lastStatusDateTime = DateTime.now().toDate();
    }
}
