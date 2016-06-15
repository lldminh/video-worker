package com.golftec.teaching.view;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by hoang on 2015-09-23.
 */
public class LessonReportModel {

    public String lessonId = "";
    public String lessonDataLink = "";
    public String storeId = "";
    public String coachId = "";
    public String coachName = "";
    public String wuci = "";
    public String studentName = "";
    public String lessonState = "";
    public String lessonStateNote = "";
    public String lessonStarted = "";
    public String lessonLastUpdated = "";
    public boolean isSubmittedOk;
    public boolean isUploadedToS3Ok;
    public String workflowId = "";
    public String s3UploadPath = "";
    public String coachNoteTitle = "";
    public String coachNoteContent = "";
    public String lessonSubmitLink = "";
    public boolean allVideosComposed = false;
    public String doneArchivedSubmitLink = "";
    public String getBackFromDoneArchivedLink = "";
    public String resubmittedBy = "";

    public List<TelestrationReportModel> telestrations = Lists.newArrayList();
}
