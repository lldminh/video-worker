package com.golftec.video.production.videoproduction;

import java.io.Serializable;

public interface ILessonHeader extends Serializable {
    public int getTotalFrames();

    public void setTotalFrames(int totalFrames);

    public String getWorkflowId();

    public void setWorkflowId(String workflowId);

    public String getLessonId();

    public void setLessonId(String lessonId);

    public int getFps();

    public void setFps(int fps);

    public int getLeftTotalUnique();

    public void setLeftTotalUnique(int totalUnique);

    public int getRightTotalUnique();

    public void setRightTotalUnique(int totalUnique);

    public String[] getLeftSource();

    public void setLeftSource(String leftSource);

    public String[] getRightSource();

    public void setRightSource(String rightSource);

    public String getLocalVersion();

    public void setLocalVersion(String versionNumber);

    public String getOsVersion();

    public void setOsVersion(String osVersion);

    public String getCoachId();

    public void setCoachId(String coachId);

    public String getCoachName();

    public void setCoachName(String coachName);

    public long getLessonDate();

    public void setLessonDate(long lessonDate);
}
