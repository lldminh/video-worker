package com.golftec.video.production.videoproduction;

public class LessonData implements ILessonData {

    private ILessonHeader lessonHeader;
    private byte[] soundBytes;

    public ILessonHeader getHeaderData() {
        return lessonHeader;
    }

    public void setHeaderData(ILessonHeader lessonHeader) {
        this.lessonHeader = lessonHeader;
    }

    public byte[] getWave() {
        return soundBytes;
    }

    /**
     * This method will take bytes of any type, but it is expected that the
     * bytes have been compressed.
     *
     * @param soundBytes
     */
    public void setWave(byte[] soundBytes) {
        this.soundBytes = soundBytes;
    }

    //public void addRightDrawingHistory(ToolBoardHistory toolBoardHistory) {
    // TODO Auto-generated method stub

    //}

    //public void addLeftMotionHistory(MotionHistory motionHistory) {
    // TODO Auto-generated method stub

    //}

    //public void addRightMotionHistory(MotionHistory motionHistory) {
    // TODO Auto-generated method stub

    //}

    //public void addLeftSwingVideoHistory(SwingVideoHistory swingVideoHistory) {
    // TODO Auto-generated method stub

    //}

    //public void addRightSwingVideoHistory(SwingVideoHistory swingVideoHistory) {
    // TODO Auto-generated method stub

    //}

    //public void addLeftDrawingHistory(ToolBoardHistory toolBoardHistory) {

    //}

    //public ToolBoardHistory getLeftDrawingHistory() {

    //}

    //public void addRightDrawingHistory(ToolBoardHistory toolBoardHistory) {

    //}

    //public ToolBoardHistory getRightDrawingHistory() {

    //}

    //public void addLeftMotionHistory(MotionHistory motionHistory) {

    //}

    //public MotionHistory getLeftMotionHistory() {

    //}

    //public void addRightMotionHistory(MotionHistory motionHistory) {

    //}

    //public MotionHistory getRightMotionHistory () {

    //}

    //public void addLeftSwingVideoHistory(SwingVideoHistory swingVideoHistory) {

    //}

    //public SwingVideoHistory getLeftSwingVideoHistory() {

    //}
    //public void addRightSwingVideoHistory(SwingVideoHistory swingVideoHistory) {

    //}

    //public SwingVideoHistory getRightSwingVideoHistory() {

    //}

}
