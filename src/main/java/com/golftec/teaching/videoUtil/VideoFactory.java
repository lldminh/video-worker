//package com.golftec.teaching.videoUtil;
//
//import com.golftec.teaching.common.GTConstants;
//import com.golftec.teaching.common.GTResponseCode;
//import com.golftec.teaching.common.GTUtil;
//import com.golftec.teaching.model.lesson.TelestrationVideo;
//import com.golftec.teaching.videoUtil.util.IPlayBackRender;
//import com.golftec.teaching.videoUtil.util.PlayBackRender;
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Consumer;
//
///**
// * Created by Teo on 2015-06-23.
// */
//public class VideoFactory {
//
//    private static final Logger log = LoggerFactory.getLogger(VideoFactory.class);
//
//    private TelestrationVideo telestrationVideo = null;
//
//    public VideoFactory(TelestrationVideo telestrationVideo) {
//        this.telestrationVideo = telestrationVideo;
//    }
//
//    public TelestrationVideo getTelestrationVideo() {
//        return this.telestrationVideo;
//    }
//
//    public void setTelestrationVideo(TelestrationVideo telestrationVideo) {
//        this.telestrationVideo = telestrationVideo;
//    }
//
//    public boolean start(Consumer<String> callback) {
//        log.info("VideoFactory check-mark 1, {}", telestrationVideo.getTelestrationId());
//        try {
//            if (this.telestrationVideo == null) {
//                return false;
//            }
//
//            try {
//                GTUtil.deleteFileSafely(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "snapshot"));
//                GTUtil.deleteFileSafely(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "output"));
//                GTUtil.deleteFileSafely(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "temp"));
//
//                Files.createDirectories(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "snapshot"));
//                Files.createDirectories(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "output"));
//                Files.createDirectories(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR, "temp"));
//            } catch (IOException e) {
//                log.error("", e);
//            }
//
//            log.info("VideoFactory check-mark 1.1, {}", telestrationVideo.getTelestrationId());
//            SwingUtilities.invokeLater(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        log.info("VideoFactory check-mark 2, {}", telestrationVideo.getTelestrationId());
//                        new JFXPanel(); // this will prepare JavaFX toolkit and environment
//                        log.info("VideoFactory check-mark 3, {}", telestrationVideo.getTelestrationId());
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                PlayBackRender playBackRender = new PlayBackRender(new IPlayBackRender() {
//                                    @Override
//                                    public void renderCompleted() {
//                                        try {
//                                            log.info("VideoFactory check-mark 4, {}", telestrationVideo.getTelestrationId());
//                                            GTUtil.deleteFileSafely(GTConstants.BASE_VIDEO_FACTORY_DIR + "output/out.mp4");
//                                            GTUtil.deleteFileSafely(GTConstants.BASE_VIDEO_FACTORY_DIR + "output/output.mp4");
//
//                                            String cmd = "ffmpeg  -i " + GTConstants.BASE_VIDEO_FACTORY_DIR + "output/combine/%01d.png -vf scale=640:480 -r 30 -pix_fmt yuv420p " + GTConstants.BASE_VIDEO_FACTORY_DIR + "output/out.mp4";
//
//                                            Process pro = Runtime.getRuntime().exec(cmd);
//                                            pro.waitFor(60000, TimeUnit.MILLISECONDS);
//
//                                            log.info("VideoFactory check-mark 5, {}", telestrationVideo.getTelestrationId());
//                                            cmd = "ffmpeg -i " + GTConstants.BASE_VIDEO_FACTORY_DIR + "output/out.mp4 -i " + telestrationVideo.getSoundFilePath() + " -c:v copy -c:a aac -strict experimental -map 0:v:0 -map 1:a:0 -shortest " + GTConstants.BASE_VIDEO_FACTORY_DIR + "output/output.mp4";
//
//                                            pro = Runtime.getRuntime().exec(cmd);
//                                            pro.waitFor();
//
//                                            log.info("VideoFactory check-mark 6, {}", telestrationVideo.getTelestrationId());
//                                            callback.accept(GTResponseCode.Ok.toString());
////                                            System.exit(0);
//                                        } catch (Exception ex) {
//                                            log.error("", ex);
//                                            callback.accept(GTResponseCode.NotOk.toString());
//                                        }
//                                    }
//                                });
//
//                                log.info("VideoFactory check-mark 7, {}", telestrationVideo.getTelestrationId());
//                                telestrationVideo.getLeftVideoHistory().setFilePath();
//                                telestrationVideo.getLeftVideoHistory().setFilePathForStandBy();
//                                playBackRender.setVideoHistoryForLeft(telestrationVideo.getLeftVideoHistory());
//
//                                playBackRender.setAnnotationHistoryForLeft(telestrationVideo.getLeftAnnotationHistory());
//
//                                playBackRender.setToolBoardHistory(telestrationVideo.getToolBoardHistory());
//                                playBackRender.setVideoLength(telestrationVideo.getVideoLength());
//
//                                telestrationVideo.getRightVideoHistory().setFilePath();
//                                telestrationVideo.getRightVideoHistory().setFilePathForStandBy();
//                                playBackRender.setVideoHistoryForRight(telestrationVideo.getRightVideoHistory());
//                                playBackRender.setAnnotationHistoryForRight(telestrationVideo.getRightAnnotationHistory());
//
//                                log.info("VideoFactory check-mark 8, {}", telestrationVideo.getTelestrationId());
//                                playBackRender.start();
//                            }
//                        });
//                    } catch (Exception ex) {
//                        log.error("", ex);
//                    }
//                }
//            });
//            log.info("VideoFactory check-mark 9, {}", telestrationVideo.getTelestrationId());
//            return true;
//        } catch (Exception ex) {
//            log.error("", ex);
//        }
//        log.info("VideoFactory check-mark 10, {}", telestrationVideo.getTelestrationId());
//        return false;
//    }
//}
