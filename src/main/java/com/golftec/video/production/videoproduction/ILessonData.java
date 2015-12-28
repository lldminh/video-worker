package com.golftec.video.production.videoproduction;

import java.io.Serializable;

public interface ILessonData extends Serializable {

    public ILessonHeader getHeaderData();

    public void setHeaderData(ILessonHeader lessonHeader);

    public byte[] getWave();

    public void setWave(byte[] soundBytes);
    //public void addLeftDrawingHistory(ToolBoardHistory toolBoardHistory);
    //public ToolBoardHistory getLeftDrawingHistory();
    //public void addRightDrawingHistory(ToolBoardHistory toolBoardHistory);
    //public ToolBoardHistory getRightDrawingHistory();
    //public void addLeftMotionHistory(MotionHistory motionHistory);
    //public MotionHistory getLeftMotionHistory();
    //public void addRightMotionHistory(MotionHistory motionHistory);
    //public MotionHistory getRightMotionHistory();
    //public void addLeftSwingVideoHistory(SwingVideoHistory swingVideoHistory);
    //public SwingVideoHistory getLeftSwingVideoHistory();
    //public void addRightSwingVideoHistory(SwingVideoHistory swingVideoHistory);
    //public SwingVideoHistory getRightSwingVideoHistory();

}
