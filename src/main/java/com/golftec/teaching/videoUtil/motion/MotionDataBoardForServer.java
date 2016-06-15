package com.golftec.teaching.videoUtil.motion;

import com.golftec.teaching.videoUtil.annotation.ToolBoard;
import com.golftec.teaching.videoUtil.history.MotionHistory;
import com.golftec.teaching.videoUtil.history.MotionStandByData;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;

public class MotionDataBoardForServer {

    int FPS = 30;
    long frame = 0;
    Timeline timeline = null;
    private AnchorPane mainPane = null;
    private List<MotionDataView> list = null;
    private ToolBoard toolBoard = null;
    private String exportFolder = "";
    private Map imgList = null;
    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            Image image = getSnapShot();
            System.out.println("adding motion png to memory");
            frame++;

//            saveImageIntoDisk(image, srcFrame);
            imgList.put(frame, image);
        }
    };

    private void saveImageIntoDisk(Image image, long frame){
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    File outputFile = new File(exportFolder + frame + ".png");
                    try {

                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
                    } catch (Exception s) {
                        System.out.println(s);
                    }
                });
            }
        };
        timerTask.run();
    }

    private MotionHistory motionHistory = null;
    private boolean isRecording = false;

    public MotionDataBoardForServer() {

        this.mainPane = new AnchorPane();

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

    public void startRecord() {
        this.motionHistory.startRecord();
        this.isRecording = true;
    }

    public void startExportImage(String exportFolder) {
        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
            file.mkdirs();
        }

        this.exportFolder = exportFolder;
        timeline.playFromStart();
    }

    public void stopExportImage() {
        timeline.stop();
    }

    public void stopRecord() {
//        this.isRecording = false;
//        this.motionHistory.serialize();
    }

    public void showHistory(MotionHistory motionHistory) {
        this.motionHistory = motionHistory;
        this.motionHistory.showHistory(this);
    }

    public void cancelShowingHistory() {
        this.motionHistory.cancelShowHistory();
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

    public void standByHistory() {
        this.motionHistory.pause();
//        this.motionHistory.setStatus((byte) 1);
    }

    public void continueHistory() {
        this.motionHistory.resumeHistory();
//        this.motionHistory.setStatus((byte) 0);
    }

    public MotionHistory getMotionHistory() {
        return this.motionHistory;
    }

    public void setMotionHistory(MotionHistory motionHistory) {
        this.motionHistory = motionHistory;
    }

    public void addStandByHistory() {
        MotionStandByData motionStandByData = new MotionStandByData();

        for (int i = 0; i < list.size(); i++) {
            MotionDataView motionDataView = list.get(i);
            MotionViewData motionViewData = new MotionViewData();
            motionViewData.setMotionDataType(motionDataView.getMotionDataType());
            motionViewData.setX(motionDataView.getPosition().getX());
            motionViewData.setY(motionDataView.getPosition().getY());
            motionStandByData.list.add(motionViewData);
        }

        motionHistory.addStandby(motionStandByData);
    }

    public void saveSnapshotIntoDisk() {
        stopExportImage();

//        File file = new File("snapshot/motion/");
        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
            file.mkdirs();
        }

        for (long frame = 1; frame < imgList.size(); frame++) {
            WritableImage image = (WritableImage) imgList.get(frame);
            File outputFile = new File(exportFolder + frame + ".png");

            try {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }

        imgList = null;
    }

    public Image getSnapShot() {

        double width = this.mainPane.getWidth();
        double height = this.mainPane.getHeight();

        if (width == 0) width = 320;
        if (height == 0) height = 480;

        WritableImage wImage = new WritableImage((int) width, (int) height);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        wImage = this.mainPane.snapshot(sp, null);

        return wImage;
    }

    public AnchorPane getMainPane() {
        return this.mainPane;
    }

    public void add(MotionDataView motionDataView) {
        motionDataView.setMotionDataBoardForServer(this);
        motionDataView.setAlpha(0.5);
        this.list.add(motionDataView);
        this.mainPane.getChildren().add(motionDataView.getPane());
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
