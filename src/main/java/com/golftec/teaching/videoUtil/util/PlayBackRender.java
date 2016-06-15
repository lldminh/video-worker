//package com.golftec.teaching.videoUtil.util;
//
//import com.golftec.teaching.common.GTConstants;
//import com.golftec.teaching.common.GTResponseCode;
//import com.golftec.teaching.videoUtil.annotation.AnnotationPane;
//import com.golftec.teaching.videoUtil.annotation.IToolBoard;
//import com.golftec.teaching.videoUtil.annotation.ToolBoard;
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
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//
//import javax.swing.*;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.function.Consumer;
//
//public class PlayBackRender implements IToolBoard {
//
//    MediaPlayerControlForServer mediaPlayerControllerForLeft = null;
//    MediaPlayerControlForServer mediaPlayerControllerForRight = null;
//    AnnotationPane annotationPaneForLeft = null;
//    AnnotationPane annotationPaneForRight = null;
//    ToolBoard toolBoard = null;
//    MotionDataBoardForServer motionDataBoardForLeft = null;
//    MotionDataBoardForServer motionDataBoardForRight = null;
//    MotionDataSet motionDataSetForLeft = null;
//    MotionDataSet motionDataSetForRight = null;
//    //    VideoHistory videoHistory = null;
//    IPlayBackRender iPlayBackRender = null;
//
//    AnnotationHistory annotationHistoryForLeft = null;
//    AnnotationHistory annotationHistoryForRight = null;
//    VideoHistory videoHistoryForLeft = null;
//    VideoHistory videoHistoryForRight = null;
//    MotionHistory motionHistoryForLeft = null;
//    MotionHistory motionHistoryForRight = null;
//
//    long videoLength = 0;
//
//    ToolBoardHistory toolBoardHistory = null;
//
//    public PlayBackRender(IPlayBackRender iPlayBackRender) {
//        this.iPlayBackRender = iPlayBackRender;
////        mediaPlayerController = new MediaPlayerControlForServer("C:\\sample.mp4", false, this);
////        mediaPlayerController.setFFrame(2000);
////        mediaPlayerController.setAFrame(4000);
////        mediaPlayerController.setTFrame(6000);
////        mediaPlayerController.setIFrame(8000);
//
//        annotationPaneForLeft = new AnnotationPane();
//        annotationPaneForRight = new AnnotationPane();
//        toolBoard = new ToolBoard(this);
//
//        motionDataBoardForLeft = new MotionDataBoardForServer();
//        motionDataBoardForLeft.setToolBoard(toolBoard);
//
//        motionDataBoardForRight = new MotionDataBoardForServer();
//        motionDataBoardForRight.setToolBoard(toolBoard);
//
//        motionDataSetForLeft = new MotionDataSet();
//        motionDataSetForRight = new MotionDataSet();
//
//        motionDataSetForLeft = Common.simulateData();
//        motionDataSetForRight = Common.simulateData();
//    }
//
//    public long getVideoLength() {
//        return this.videoLength;
//    }
//
//    public void setVideoLength(long videoLength) {
//        this.videoLength = videoLength;
//    }
//
//    public void setAnnotationHistoryForLeft(AnnotationHistory annotationHistoryForLeft) {
//        this.annotationHistoryForLeft = annotationHistoryForLeft;
//    }
//
//    public void setAnnotationHistoryForRight(AnnotationHistory annotationHistoryForRight) {
//        this.annotationHistoryForRight = annotationHistoryForRight;
//    }
//
//    public void setVideoHistoryForLeft(VideoHistory videoHistoryForLeft) {
//        this.videoHistoryForLeft = videoHistoryForLeft;
//    }
//
//    public void setVideoHistoryForRight(VideoHistory videoHistoryForRight) {
//        this.videoHistoryForRight = videoHistoryForRight;
//    }
//
//    public void setMotionHistoryForLeft(MotionHistory motionHistoryForLeft) {
//        this.motionHistoryForLeft = motionHistoryForLeft;
//    }
//
//    public void setMotionHistoryForRight(MotionHistory motionHistoryForRight) {
//        this.motionHistoryForRight = motionHistoryForRight;
//    }
//
//    public void setToolBoardHistory(ToolBoardHistory toolBoardHistory) {
//        this.toolBoardHistory = toolBoardHistory;
//    }
//
//    public void exportLeftVideo(Consumer<String> processedCallback) {
//        if (videoHistoryForLeft != null) {
//            mediaPlayerControllerForLeft.showHistory(videoHistoryForLeft);
//            mediaPlayerControllerForLeft.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/video/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        mediaPlayerControllerForLeft.stopExportImage();
//                        mediaPlayerControllerForLeft.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }
//    }
//
//    public void exportRightVideo(Consumer<String> processedCallback) {
//        if (videoHistoryForRight != null) {
//            mediaPlayerControllerForRight.showHistory(videoHistoryForRight);
//            mediaPlayerControllerForRight.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/video/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        mediaPlayerControllerForRight.stopExportImage();
//                        mediaPlayerControllerForRight.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }
//    }
//
//    public void exportLeftAnnotation(Consumer<String> processedCallback) {
//        if (annotationHistoryForLeft != null) {
//            annotationPaneForLeft.showHistory(annotationHistoryForLeft);
//            if (toolBoardHistory != null) {
//                toolBoard.showHistory(toolBoardHistory);
//            }
//            annotationPaneForLeft.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/annotation/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        annotationPaneForLeft.stopExportImage();
//                        annotationPaneForLeft.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }
//    }
//
//    public void exportRightAnnotation(Consumer<String> processedCallback) {
//        if (annotationHistoryForRight != null) {
//            annotationPaneForRight.showHistory(annotationHistoryForRight);
//            if (toolBoardHistory != null) {
//                toolBoard.showHistory(toolBoardHistory);
//            }
//            annotationPaneForRight.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/annotation/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        annotationPaneForRight.stopExportImage();
//                        annotationPaneForRight.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }
//    }
//
//    public void exportLeftMotion(Consumer<String> processedCallback) {
//        if (motionHistoryForLeft != null) {
//            motionDataBoardForLeft.showHistory(motionHistoryForLeft);
//            motionDataBoardForLeft.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/motion/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        motionDataBoardForLeft.stopExportImage();
//                        motionDataBoardForLeft.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }else {
//            processedCallback.accept(GTResponseCode.Ok.toString());
//        }
//    }
//
//    public void exportRightMotion(Consumer<String> processedCallback) {
//        if (motionHistoryForRight != null) {
//            motionDataBoardForRight.showHistory(motionHistoryForRight);
//            motionDataBoardForRight.startExportImage(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/motion/");
//
//            try {
//                Timer timer = new Timer();
//                TimerTask timerTask = new TimerTask() {
//                    @Override
//                    public void run() {
//                        motionDataBoardForRight.stopExportImage();
//                        motionDataBoardForRight.saveSnapshotIntoDisk();
//                        System.gc();
//                        processedCallback.accept(GTResponseCode.Ok.toString());
//                    }
//                };
//                timer.schedule(timerTask, videoLength);
//            } catch (Exception ex) {
//                System.out.println(ex);
//                processedCallback.accept(GTResponseCode.NotOk.toString());
//            }
//        }else {
//            processedCallback.accept(GTResponseCode.Ok.toString());
//        }
//    }
//
//    private void makeVideo() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new JFXPanel(); // this will prepare JavaFX toolkit and environment
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        VideoMaker videoMaker = new VideoMaker();
//                        videoMaker.start(GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/video/",
//                                         GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/annotation/",
//                                         GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/left/motion/",
//                                         GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/video/",
//                                         GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/annotation/",
//                                         GTConstants.BASE_VIDEO_FACTORY_DIR + "snapshot/right/motion/");
////                        videoMaker.saveIntoDisk();
//
//                        iPlayBackRender.renderCompleted();
//                    }
//                });
//            }
//        });
//    }
//
//    public void start() {
//
//        //Left
//        mediaPlayerControllerForLeft = new MediaPlayerControlForServer("C:\\test.mp4", false, new IVideoEvent() {
//            @Override
//            public void frameChanged(int frame) {
//                List<MotionData> list = motionDataSetForLeft.get(frame);
//                motionDataBoardForLeft.show(list);
//            }
//
//            @Override
//            public void started() {
//
//            }
//        });
//
//        mediaPlayerControllerForRight = new MediaPlayerControlForServer("C:\\test.mp4", false, new IVideoEvent() {
//            @Override
//            public void frameChanged(int frame) {
//                List<MotionData> list = motionDataSetForRight.get(frame);
//                motionDataBoardForRight.show(list);
//            }
//
//            @Override
//            public void started() {
//
//            }
//        });
//
//        exportLeftVideo(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                exportRightVideo(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        exportLeftAnnotation(new Consumer<String>() {
//                            @Override
//                            public void accept(String s) {
//                                exportRightAnnotation(new Consumer<String>() {
//                                    @Override
//                                    public void accept(String s) {
//                                        exportLeftMotion(new Consumer<String>() {
//                                            @Override
//                                            public void accept(String s) {
//                                                exportRightMotion(new Consumer<String>() {
//                                                    @Override
//                                                    public void accept(String s) {
//                                                        makeVideo();
//                                                    }
//                                                });
//                                            }
//                                        });
//                                    }
//                                });
//                            }
//                        });
//                    }
//                });
//            }
//        });
//    }
//
//    @Override
//    public void consumeAngle() {
//        annotationPaneForLeft.setiToolable(new GTAngle());
//        annotationPaneForRight.setiToolable(new GTAngle());
//    }
//
//    @Override
//    public void consumeErase() {
//        GTErase gtErase1 = new GTErase(annotationPaneForLeft.getMainLayer());
//        gtErase1.setHighLightCanvasBoard(annotationPaneForLeft.getHighLightLayer());
//        annotationPaneForLeft.setiToolable(gtErase1);
//
//        GTErase gtErase2 = new GTErase(annotationPaneForRight.getMainLayer());
//        gtErase2.setHighLightCanvasBoard(annotationPaneForRight.getHighLightLayer());
//        annotationPaneForRight.setiToolable(gtErase2);
//    }
//
//    @Override
//    public void consumeRefresh() {
//        annotationPaneForLeft.refresh();
//        annotationPaneForRight.refresh();
//    }
//
//    @Override
//    public void consumeBack() {
//        annotationPaneForLeft.back();
//        annotationPaneForRight.back();
//    }
//
//    @Override
//    public void consumeLine() {
//        annotationPaneForLeft.setiToolable(new GTLine());
//        annotationPaneForRight.setiToolable(new GTLine());
//    }
//
//    @Override
//    public void consumeOval() {
//        annotationPaneForLeft.setiToolable(new GTOval());
//        annotationPaneForRight.setiToolable(new GTOval());
//    }
//
//    @Override
//    public void consumeCircle() {
//        annotationPaneForLeft.setiToolable(new GTCircle());
//        annotationPaneForRight.setiToolable(new GTCircle());
//    }
//
//    @Override
//    public void consumeArrow() {
//        annotationPaneForLeft.setiToolable(new GTArrow());
//        annotationPaneForRight.setiToolable(new GTArrow());
//    }
//
//    @Override
//    public void consumeRectangle() {
//        annotationPaneForLeft.setiToolable(new GTRectangle());
//        annotationPaneForRight.setiToolable(new GTRectangle());
//    }
//
//    @Override
//    public void consumeSquare() {
//        annotationPaneForLeft.setiToolable(new GTSquare());
//        annotationPaneForRight.setiToolable(new GTSquare());
//    }
//
//    @Override
//    public void consumeConstrantLine() {
//        annotationPaneForLeft.setiToolable(new GTConstrantLine());
//        annotationPaneForRight.setiToolable(new GTConstrantLine());
//    }
//
//    @Override
//    public void consumeConstrantArrow() {
//        annotationPaneForLeft.setiToolable(new GTConstrantArrow());
//        annotationPaneForRight.setiToolable(new GTConstrantArrow());
//    }
//
//    @Override
//    public void consumePencil() {
//        annotationPaneForLeft.setiToolable(new GTPencil());
//        annotationPaneForRight.setiToolable(new GTPencil());
//    }
//
//    @Override
//    public void consumeCursor() {
//        annotationPaneForLeft.setiToolable(new GTCursor());
//        annotationPaneForRight.setiToolable(new GTCursor());
//    }
//
//    @Override
//    public void consumeBlue() {
//        annotationPaneForLeft.setColor(GTColor.BLUE);
//        annotationPaneForRight.setColor(GTColor.BLUE);
//    }
//
//    @Override
//    public void consumeYellow() {
//        annotationPaneForLeft.setColor(GTColor.YELLOW);
//        annotationPaneForRight.setColor(GTColor.YELLOW);
//    }
//
//    @Override
//    public void consumeRed() {
//        annotationPaneForLeft.setColor(GTColor.RED);
//        annotationPaneForRight.setColor(GTColor.RED);
//    }
//
//    @Override
//    public void consumeGreen() {
//        annotationPaneForLeft.setColor(GTColor.GREEN);
//        annotationPaneForRight.setColor(GTColor.GREEN);
//    }
//
//    @Override
//    public void consumeEmpty() {
//        annotationPaneForLeft.setiToolable(null);
//        annotationPaneForRight.setiToolable(null);
//    }
//}
