package com.golftec.teaching.videoUtil.motion;

import com.golftec.teaching.videoUtil.annotation.ToolBoard;
import com.golftec.teaching.videoUtil.history.MotionHistory;
import com.golftec.teaching.videoUtil.history.MotionStandByData;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sonleduc on 3/14/15.
 */

public class MotionDataBoard {
    int FPS = 30;
    long frame = 0;
    Timeline timeline = null;
    private AnchorPane pane = null;
    private List<MotionDataView> list = null;
    private ToolBoard toolBoard = null;
    private Map imgList = null;
    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            Image image = getSnapShot();

            frame++;
            imgList.put(frame, image);
        }
    };
    private String exportFolder = "";
    private MotionHistory motionHistory = null;
    private boolean isRecording = false;

    public void cleanHistory() {
        this.motionHistory.cleanHistory();
    }

    public MotionDataBoard(AnchorPane pane) {
        this.pane = pane;
        this.list = new ArrayList<MotionDataView>();
        this.motionHistory = new MotionHistory();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        double duration = 1000 / FPS;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));

        imgList = new HashMap();
    }

    public ToolBoard getToolBoard() {
        return this.toolBoard;
    }

    public void setToolBoard(ToolBoard toolBoard) {
        this.toolBoard = toolBoard;
    }

    public MotionHistory getMotionHistory(){
        return  this.motionHistory;
    }

    public void setMotionHistory(MotionHistory motionHistory) {
        this.motionHistory = motionHistory;
    }

    public void startRecord() {
        this.motionHistory.startRecord();
        this.isRecording = true;
    }

    public void startExportImage(String exportFolder) {
        this.exportFolder = exportFolder;
        timeline.playFromStart();
    }

    public void stopExportImage() {
        timeline.stop();
    }

    public void stop() {
        this.isRecording = false;
    }

    public void stopRecordAndSave(String filePath, String fileName) {
        this.isRecording = false;
//        this.motionHistory.serialize(filePath, fileName);
    }

    public void showHistory(MotionHistory motionHistory) {
        this.motionHistory = motionHistory;
        this.motionHistory.showHistory(this);
    }

    public void cancelShowingHistory(){
        this.motionHistory.cancelShowHistory();
    }

    public void saveSnapshotIntoDisk() {
        stopExportImage();
        for (long frame = 1; frame < imgList.size(); frame++) {
            WritableImage image = (WritableImage) imgList.get(frame);
            File outputFile = new File(exportFolder + frame + ".png");

            try {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }
    }

    public Image getSnapShot() {
        double width = this.pane.getWidth();
        double height = this.pane.getHeight();

        if (width == 0) width = 320;
        if (height == 0) height = 480;

        WritableImage wImage = new WritableImage((int) width, (int) height);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        wImage = this.pane.snapshot(sp, null);

        return wImage;
    }

    public void add(MotionDataView motionDataView) {
        motionDataView.setMotionDataBoard(this);
        motionDataView.setAlpha(0.5);
        this.list.add(motionDataView);
        this.pane.getChildren().add(motionDataView.getPane());
    }

    public MotionDataView getItemByXY(GTMouseEvent gtMouseEvent) {
        for (int i = 0; i < list.size(); i++) {
            MotionDataView motionDataView = list.get(i);
            Pane pane = motionDataView.getPane();
            BoundingBox b = new BoundingBox(pane.getLayoutX(), pane.getLayoutY(), pane.getWidth(), pane.getHeight());

            if (b.contains(new Point2D(gtMouseEvent.getX(), gtMouseEvent.getY()))) {
                return motionDataView;
            }
        }
        return null;
    }

    public void addStandByHistory() {
        MotionStandByData motionStandByData = new MotionStandByData();

        for(int i = 0; i < list.size(); i++){
            MotionDataView motionDataView = list.get(i);
            MotionViewData motionViewData = new MotionViewData();
            motionViewData.setMotionDataType(motionDataView.getMotionDataType());
            motionViewData.setX(motionDataView.getPosition().getX());
            motionViewData.setY(motionDataView.getPosition().getY());
            motionStandByData.list.add(motionViewData);
        }

        motionHistory.addStandby(motionStandByData);
    }

    public void standByHistory() {
        this.motionHistory.pause();
//        this.motionHistory.setStatus((byte) 1);
    }

    public void continueHistory() {
        this.motionHistory.resumeHistory();
//        this.motionHistory.setStatus((byte) 0);
    }

    public void consumeStandByEvent(MotionStandByData motionStandByData) {
        list.clear();
        for (int i = 0; i < motionStandByData.list.size(); i++) {
            MotionViewData motionViewData = motionStandByData.list.get(i);
            MotionDataView motionDataView = new MotionDataView(motionViewData.getX(), motionViewData.getY(),
                    motionViewData.getMotionDataType());
            add(motionDataView);
        }
    }

    public void consumeEvent(MotionEvent motionEvent) {
        if (this.isRecording) {
            this.motionHistory.add(motionEvent);
        }

        switch (motionEvent.method) {
            case "add": {
                MotionDataView motionDataView = new MotionDataView(motionEvent.gtMouseEvent.getX(), motionEvent.gtMouseEvent.getY(), motionEvent.motionDataType);
                motionDataView.setAlpha(0.5);
                this.add(motionDataView);
                break;
            }
            case "move": {
                moveTo(motionEvent.gtMouseEvent);
                break;
            }
            case "select": {
                MotionDataView motionDataView = getItemByXY(motionEvent.gtMouseEvent);
                if (motionDataView != null) {
                    motionDataView.setSelected(true);
                }
                break;
            }
        }
    }

    public void show(MotionData motionData) {
        for (int i = 0; i < list.size(); i++) {
            MotionDataView motionDataView = list.get(i);
            if (motionDataView.getMotionDataType() == motionData.getMotionDataType()) {
                motionDataView.setMotionData(motionData);
            }
        }
    }

    public void moveTo(GTMouseEvent gtMouseEvent) {
        for (int i = 0; i < list.size(); i++) {
            MotionDataView motionDataView = list.get(i);
            if (motionDataView.isSelected()) {
                motionDataView.getPane().setLayoutX(gtMouseEvent.getX());
                motionDataView.getPane().setLayoutY(gtMouseEvent.getY());
                resetSelected();
            }
        }

    }

    public void resetSelected() {
        for (int i = 0; i < list.size(); i++) {
            MotionDataView motionDataView = list.get(i);
            motionDataView.setSelected(false);
        }
    }

    public void show(List<MotionData> motionDataList) {
        if (motionDataList != null && motionDataList.size() > 0) {
            for (int j = 0; j < motionDataList.size(); j++) {
                MotionData motionData = motionDataList.get(j);
                for (int i = 0; i < list.size(); i++) {
                    MotionDataView motionDataView = this.list.get(i);
                    if (motionData.getMotionDataType() != null) {
                        if (motionDataView.getMotionDataType() == motionData.getMotionDataType()) {
                            motionDataView.setMotionData(motionData);
                        }
                    }

                }
            }
        }
    }

}
