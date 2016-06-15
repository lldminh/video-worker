//package com.golftec.teaching.videoUtil.preview;
//
//import com.golftec.teaching.common.GTUtil;
//import com.golftec.teaching.videoUtil.annotation.AnnotationPane;
//import com.golftec.teaching.videoUtil.annotation.IToolBoard;
//import com.golftec.teaching.videoUtil.annotation.ToolBoard;
//import com.golftec.teaching.videoUtil.audio.AudioPlayer;
//import com.golftec.teaching.videoUtil.control.IVideoEvent;
//import com.golftec.teaching.videoUtil.control.MediaPlayerControlForServer;
//import com.golftec.teaching.videoUtil.drawingTool.*;
//import com.golftec.teaching.videoUtil.history.AnnotationHistory;
//import com.golftec.teaching.videoUtil.history.MotionHistory;
//import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
//import com.golftec.teaching.videoUtil.history.VideoHistory;
//import com.golftec.teaching.videoUtil.motion.MotionData;
//import com.golftec.teaching.videoUtil.motion.MotionDataBoardForServer;
//import com.golftec.teaching.videoUtil.motion.MotionDataSet;
//import com.golftec.teaching.videoUtil.util.Common;
//import com.golftec.teaching.videoUtil.util.GTColor;
//import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Button;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//import javafx.scene.layout.VBox;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class VideoPreview extends VBox implements IToolBoard, Initializable {
//
//    public Pane leftVideoPane;
//    public Pane rightVideoPane;
//
//    public Button btnCancel;
//    public Button btnDelete;
//    public Button btnDone;
//    MediaPlayerControlForServer leftMediaPlayerController = null;
//    MediaPlayerControlForServer rightMediaPlayerController = null;
//    AnnotationPane leftAnnotationPane = null;
//    AnnotationPane rightAnnotationPane = null;
//    ToolBoard toolBoard = null;
//    MotionDataBoardForServer leftMotionDataBoard = null;
//    MotionDataBoardForServer rightMotionDataBoard = null;
//    MotionDataSet motionDataSet = null;
//    VideoHistory leftVideoHistory = null;
//    VideoHistory rightVideoHistory = null;
//    IVideoPreview iVideoPreview = null;
//    String audioFile = "";
//    AudioPlayer audioPlayer = null;
//    Pane videoPane = new Pane();
//    private String path = "";
//
//    public VideoPreview(String path, IVideoPreview iVideoPreview) {
//        this.iVideoPreview = iVideoPreview;
//
//        this.path = path;
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/preview.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(this);
//
//        try {
//            fxmlLoader.load();
//        } catch (IOException exception) {
//            throw new RuntimeException(exception);
//        }
//
//        videoPane.setPrefWidth(315);
//        videoPane.setPrefHeight(457);
//
//        motionDataSet = new MotionDataSet();
//        motionDataSet = Common.simulateData();
//
//        leftMediaPlayerController = new MediaPlayerControlForServer("temp/1cade701-3a2b-4cd9-a103-8727cbf4d2f0.mp4", false, new IVideoEvent() {
//            @Override
//            public void frameChanged(int frame) {
//                List<MotionData> list = motionDataSet.get(frame);
//                leftMotionDataBoard.show(list);
//            }
//
//            @Override
//            public void started() {
//                try {
//
////                    Timer timer = new Timer();
////
////                    TimerTask timerTask = new TimerTask() {
////                        @Override
////                        public void run() {
////                            leftMediaPlayerController.dispose();
////                        }
////                    };
////
////                    timer.schedule(timerTask, leftVideoHistory.getVideoLength());
//
//
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
//        });
//
//        rightMediaPlayerController = new MediaPlayerControlForServer("/Users/sonleduc/Documents/test.mp4", false, new IVideoEvent() {
//            @Override
//            public void frameChanged(int frame) {
//                List<MotionData> list = motionDataSet.get(frame);
//                rightMotionDataBoard.show(list);
//            }
//
//            @Override
//            public void started() {
//                try {
//
////                    Timer timer = new Timer();
////
////                    TimerTask timerTask = new TimerTask() {
////                        @Override
////                        public void run() {
////                            rightMediaPlayerController.dispose();
////                        }
////                    };
////
////                    timer.schedule(timerTask, rightVideoHistory.getVideoLength());
//
//
//                } catch (Exception ex) {
//                    System.out.println(ex);
//                }
//            }
//        });
//
//        leftVideoPane.getChildren().add(leftMediaPlayerController.getVideoPane());
//        rightVideoPane.getChildren().add(rightMediaPlayerController.getVideoPane());
//
//        leftAnnotationPane = new AnnotationPane();
//        rightAnnotationPane = new AnnotationPane();
//        leftVideoPane.getChildren().add(leftAnnotationPane.getMainPane());
//        rightVideoPane.getChildren().add(rightAnnotationPane.getMainPane());
//
//        toolBoard = new ToolBoard(this);
//
//        leftMotionDataBoard = new MotionDataBoardForServer();
//        rightMotionDataBoard = new MotionDataBoardForServer();
//
//        leftMotionDataBoard.setToolBoard(toolBoard);
//        rightMotionDataBoard.setToolBoard(toolBoard);
//
//        leftVideoPane.getChildren().add(leftMotionDataBoard.getMainPane());
//        rightVideoPane.getChildren().add(rightMotionDataBoard.getMainPane());
//
//    }
//
//    public String getAudioFile() {
//        return audioFile;
//    }
//
//    public void setAudioFile(String audioFile) {
//        this.audioFile = audioFile;
//    }
//
//    public void start() {
////        leftMediaPlayerController.startPlay();
//
//        leftVideoHistory = Common.deserialize(path, "leftVideo.ser", leftVideoHistory);
//        rightVideoHistory = Common.deserialize(path, "rightVideo.ser", rightVideoHistory);
//        leftMediaPlayerController.showHistory(leftVideoHistory);
//        rightMediaPlayerController.showHistory(rightVideoHistory);
//
//
//        AnnotationHistory leftAnnotationHistory = null;
//        leftAnnotationHistory = Common.deserialize(path, "leftAnnotation.ser", leftAnnotationHistory);
//        leftAnnotationPane.showHistory(leftAnnotationHistory);
//
//        String json = GTUtil.toJson(leftAnnotationHistory);
//        System.out.println(json);
//
//
//        ToolBoardHistory toolBoardHistory = null;
//        toolBoardHistory = Common.deserialize(path, "toolBoardHistory.ser", toolBoardHistory);
//        toolBoard.showHistory(toolBoardHistory);
//
//        MotionHistory leftMotionHistory = null;
//        leftMotionHistory = Common.deserialize(path, "leftMotionData.ser", leftMotionHistory);
//        leftMotionDataBoard.showHistory(leftMotionHistory);
//
//
//        AnnotationHistory rightAnnotationHistory = null;
//        rightAnnotationHistory = Common.deserialize(path, "rightAnnotation.ser", rightAnnotationHistory);
//        rightAnnotationPane.showHistory(rightAnnotationHistory);
//
//        MotionHistory rightMotionHistory = null;
//        rightMotionHistory = Common.deserialize(path, "rightMotionData.ser", rightMotionHistory);
//        rightMotionDataBoard.showHistory(rightMotionHistory);
//
//
//        audioPlayer = new AudioPlayer(audioFile);
//        audioPlayer.start();
//
//    }
//
//    public void stop() {
////        annotationPane.stopRecordAndSave();
////        toolBoard.stopRecordAndSave();
////        motionDataBoard.stopRecord();
////        mediaPlayerController.stopRecord();
//    }
//
//    public void close() {
//        try {
//            if (leftAnnotationPane != null) {
//                leftAnnotationPane.cancelShowingHistory();
//            }
//
//            if (rightAnnotationPane != null) {
//                rightAnnotationPane.cancelShowingHistory();
//            }
//
//            if (leftMotionDataBoard != null) {
//                leftMotionDataBoard.cancelShowingHistory();
//            }
//
//            if (rightMotionDataBoard != null) {
//                rightMotionDataBoard.cancelShowingHistory();
//            }
//
//            if (leftMediaPlayerController != null) {
//                leftMediaPlayerController.cancelShowingHistory();
//            }
//
//            if (rightMediaPlayerController != null) {
//                rightMediaPlayerController.cancelShowingHistory();
//            }
//
//            if (leftMediaPlayerController != null) {
//                leftMediaPlayerController.dispose();
//            }
//
//            if (rightMediaPlayerController != null) {
//                rightMediaPlayerController.dispose();
//            }
//
//            if(toolBoard != null){
//                toolBoard.cancelShowingHistory();
//            }
//
//            if (audioPlayer != null) {
//                audioPlayer.stop();
//            }
//
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    @Override
//    public void consumeAngle() {
//        leftAnnotationPane.setiToolable(new GTAngle());
//        rightAnnotationPane.setiToolable(new GTAngle());
//    }
//
//    @Override
//    public void consumeErase() {
//        GTErase gtErase1 = new GTErase(leftAnnotationPane.getMainLayer());
//        gtErase1.setHighLightCanvasBoard(leftAnnotationPane.getHighLightLayer());
//        leftAnnotationPane.setiToolable(gtErase1);
//
//        GTErase gtErase2 = new GTErase(rightAnnotationPane.getMainLayer());
//        gtErase2.setHighLightCanvasBoard(rightAnnotationPane.getHighLightLayer());
//        rightAnnotationPane.setiToolable(gtErase2);
//    }
//
//    @Override
//    public void consumeRefresh() {
//        leftAnnotationPane.refresh();
//        rightAnnotationPane.refresh();
//    }
//
//    @Override
//    public void consumeBack() {
//        leftAnnotationPane.back();
//        rightAnnotationPane.back();
//    }
//
//    @Override
//    public void consumeLine() {
//        leftAnnotationPane.setiToolable(new GTLine());
//        rightAnnotationPane.setiToolable(new GTLine());
//    }
//
//    @Override
//    public void consumeOval() {
//        leftAnnotationPane.setiToolable(new GTOval());
//        rightAnnotationPane.setiToolable(new GTOval());
//    }
//
//    @Override
//    public void consumeCircle() {
//        leftAnnotationPane.setiToolable(new GTCircle());
//        rightAnnotationPane.setiToolable(new GTCircle());
//    }
//
//    @Override
//    public void consumeArrow() {
//        leftAnnotationPane.setiToolable(new GTArrow());
//        rightAnnotationPane.setiToolable(new GTArrow());
//    }
//
//    @Override
//    public void consumeRectangle() {
//        leftAnnotationPane.setiToolable(new GTRectangle());
//        rightAnnotationPane.setiToolable(new GTRectangle());
//    }
//
//    @Override
//    public void consumeSquare() {
//        leftAnnotationPane.setiToolable(new GTSquare());
//        rightAnnotationPane.setiToolable(new GTSquare());
//    }
//
//    @Override
//    public void consumeConstrantLine() {
//        leftAnnotationPane.setiToolable(new GTConstrantLine());
//        rightAnnotationPane.setiToolable(new GTConstrantLine());
//    }
//
//    @Override
//    public void consumeConstrantArrow() {
//        leftAnnotationPane.setiToolable(new GTConstrantArrow());
//        rightAnnotationPane.setiToolable(new GTConstrantArrow());
//    }
//
//    @Override
//    public void consumePencil() {
//        leftAnnotationPane.setiToolable(new GTPencil());
//        rightAnnotationPane.setiToolable(new GTPencil());
//    }
//
//    @Override
//    public void consumeCursor() {
//        leftAnnotationPane.setiToolable(new GTCursor());
//        rightAnnotationPane.setiToolable(new GTCursor());
//    }
//
//    @Override
//    public void consumeBlue() {
//        leftAnnotationPane.setColor(GTColor.BLUE);
//        rightAnnotationPane.setColor(GTColor.BLUE);
//    }
//
//    @Override
//    public void consumeYellow() {
//        leftAnnotationPane.setColor(GTColor.YELLOW);
//        rightAnnotationPane.setColor(GTColor.YELLOW);
//    }
//
//    @Override
//    public void consumeRed() {
//        leftAnnotationPane.setColor(GTColor.RED);
//        rightAnnotationPane.setColor(GTColor.RED);
//    }
//
//    @Override
//    public void consumeGreen() {
//        leftAnnotationPane.setColor(GTColor.GREEN);
//        rightAnnotationPane.setColor(GTColor.GREEN);
//    }
//
//    @Override
//    public void consumeEmpty() {
//        leftAnnotationPane.setiToolable(null);
//        rightAnnotationPane.setiToolable(null);
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        close();
//                        iVideoPreview.closed();
//                    }
//                });
//
//        btnDone.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                        close();
//                        iVideoPreview.closed();
//                    }
//                });
//
//        btnDelete.addEventHandler(MouseEvent.MOUSE_CLICKED,
//                new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent e) {
//                    }
//                });
//    }
//}
