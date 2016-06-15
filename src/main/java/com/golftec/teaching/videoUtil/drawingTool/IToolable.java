package com.golftec.teaching.videoUtil.drawingTool;

import com.golftec.teaching.videoUtil.util.GTMouseEvent;

public interface IToolable {

    void consumeMousePress(GTMouseEvent me);

    void consumeMouseDragged(GTMouseEvent me);

    void consumeMouseMoved(GTMouseEvent me);

    void consumeMouseDragReleased(GTMouseEvent me);

    void consumeMouseReleased(GTMouseEvent me);
}
