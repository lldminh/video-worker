package com.golftec.video.production.videoproduction;

import java.util.ArrayList;
import java.util.List;

public class LessonHeader implements ILessonHeader {

    private int totalFrames = 0;
    private String workflowId = "-1";
    private String lessonId = "-1";
    private int fps = 30;
    private int leftTotalUnique = 0;
    private int rightTotalUnique = 0;
    private List<String> leftSource = new ArrayList<String>();
    private List<String> rightSource = new ArrayList<String>();
    private String localVersion = "-1";
    private String osVersion = "-1";

    public int getTotalFrames() {
        return totalFrames;
    }

    public void setTotalFrames(int totalFrames) {
        this.totalFrames = totalFrames;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getLeftTotalUnique() {
        return leftTotalUnique;
    }

    public void setLeftTotalUnique(int totalUnique) {
        this.leftTotalUnique = totalUnique;
    }

    public int getRightTotalUnique() {
        return rightTotalUnique;
    }

    public void setRightTotalUnique(int totalUnique) {
        this.rightTotalUnique = totalUnique;
    }

    public String[] getRightSource() {
        String[] right = new String[rightSource.size()];
        if (rightSource.size() > 0) {
            right = rightSource.toArray(right);
        }
        return right;
    }

    public void setRightSource(String rightSource) {
        this.rightSource.add(rightSource);
    }

    public String[] getLeftSource() {
        String[] left = new String[rightSource.size()];
        if (leftSource.size() > 0) {
            left = leftSource.toArray(left);
        }
        return left;
    }

    public void setLeftSource(String leftSource) {
        this.leftSource.add(leftSource);

    }

    public String getLocalVersion() {
        return localVersion;
    }

    public void setLocalVersion(String versionNumber) {
        this.localVersion = versionNumber;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getCoachId() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCoachId(String coachId) {
        // TODO Auto-generated method stub

    }

    public String getCoachName() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setCoachName(String coachName) {
        // TODO Auto-generated method stub

    }

    public long getLessonDate() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setLessonDate(long lessonDate) {
        // TODO Auto-generated method stub

    }

}
