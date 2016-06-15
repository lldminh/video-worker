package com.golftec.teaching.videoUtil;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.videoUtil.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Teo on 2015-07-12.
 */
public class VideoFactoryEx {

    private static final Logger log = LoggerFactory.getLogger(VideoFactoryEx.class);

    private TelestrationVideo telestrationVideo = null;

    private boolean useSourceURL = false;
    private String wrapperFolder = "XXX";
    private String outPutFolder = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/";
    private int numberOfThreads = 5;

    public VideoFactoryEx(TelestrationVideo telestrationVideo) {
        this.telestrationVideo = telestrationVideo;
        this.wrapperFolder = GTUtil.uuid() + "/";
    }

    public void setNumberOfThreads(int numberOfThreads){
        this.numberOfThreads = numberOfThreads;
    }

    public TelestrationVideo getTelestrationVideo() {
        return this.telestrationVideo;
    }

    public void setTelestrationVideo(TelestrationVideo telestrationVideo) {
        this.telestrationVideo = telestrationVideo;
    }

    public String getWrapperFolder() {
        return this.outPutFolder;
    }

    private void setWrapperFolder(String folder) {
        this.wrapperFolder = folder;
    }

    public void setOutPutFolder(String folder) {
        this.outPutFolder = folder;
    }

    public boolean start(boolean useSourceURL) {
        this.useSourceURL = useSourceURL;

        try {
            GTUtil.deleteFileSafely(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR));
            Files.createDirectories(Paths.get(GTConstants.BASE_VIDEO_FACTORY_DIR));
        } catch (Exception ex) {
        }

        try {
            Thread thread1 = new Thread() {
                public void run() {
                    try {
                        makePNGForLeftVideo();
                        // other complex code
                    } catch (Exception ex) {
                        throw new RuntimeException();
                    }
                }
            };

            thread1.start();

            Thread thread2 = new Thread() {
                public void run() {
                    try {
                        makePNGForRightVideo();
                        // other complex code
                    } catch (Exception ex) {
                        throw new RuntimeException();
                    }
                }
            };
            thread2.start();

            Thread thread3 = new Thread() {
                public void run() {
                    makePNGForLeftAnnotation();
                }
            };
            thread3.start();

            Thread thread4 = new Thread() {
                public void run() {
                    makePNGForRightAnnotation();
                }
            };
            thread4.start();

            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();

            combinePNG();

            makeVideo();
            return true;
        } catch (Exception ex) {
            log.error("ERROR when making video {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
        }
        return false;
    }

    private boolean makePNGForLeftVideo() throws Exception {
        try {
            if (telestrationVideo.getLeftVideoHistory() != null) {
                VideoRender videoRender = new VideoRender();
                videoRender.setSnapshotVideoFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                                   "snapshot/left/video/");
                videoRender.setDownloadFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "temp/left/");

                videoRender.setPNGFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                         "sourceVideoPNG/left/");

                videoRender.setVideoHistory(telestrationVideo.getLeftVideoHistory());
                videoRender.setNumberOfThreads(numberOfThreads);
                videoRender.start(this.useSourceURL);
            }
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method makePNGForLeftVideo {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    private boolean makePNGForRightVideo() throws Exception {
        try {
            if (telestrationVideo.getRightVideoHistory() != null) {
                VideoRender videoRender = new VideoRender();
                videoRender.setSnapshotVideoFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                                   "snapshot/right/video/");

                videoRender.setDownloadFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "temp/right/");

                videoRender.setPNGFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                         "sourceVideoPNG/right/");

                videoRender.setVideoHistory(telestrationVideo.getRightVideoHistory());
                videoRender.setNumberOfThreads(numberOfThreads);

                videoRender.start(this.useSourceURL);
            }
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method makePNGForRightVideo {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    private boolean makePNGForLeftAnnotation() {
        try {
            if (telestrationVideo.getLeftAnnotationHistory() != null) {
                AnnotationRender annotationRender = new AnnotationRender();
                annotationRender.setAnnotationHistory(telestrationVideo.getLeftAnnotationHistory());
                annotationRender.setSnapshotAnnotationFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                                             "snapshot/left/annotation/");
                annotationRender.setNumberOfThreads(numberOfThreads);
                annotationRender.start();
            }
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method makePNGForLeftAnnotation {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    private boolean makePNGForRightAnnotation() {
        try {
            if (telestrationVideo.getLeftAnnotationHistory() != null) {
                AnnotationRender annotationRender = new AnnotationRender();
                annotationRender.setAnnotationHistory(telestrationVideo.getRightAnnotationHistory());
                annotationRender.setSnapshotAnnotationFolder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder +
                                                             "snapshot/right/annotation/");
                annotationRender.setNumberOfThreads(numberOfThreads);
                annotationRender.start();
            }
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method makePNGForRightAnnotation {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    public boolean combinePNG() {
        try {
            VideoMakerEx videoMakerEx = new VideoMakerEx();
            videoMakerEx.setOutputFolderForLeft(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "output/left/");
            videoMakerEx.setOutputFolderForRight(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "output/right/");
            videoMakerEx.setOutPutCombineFollder(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "output/combine/");
            videoMakerEx.setNumberOfThreads(numberOfThreads);
            videoMakerEx.start(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/left/video/",
                               GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/left/annotation/",
                               GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/left/motion/",
                               GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/right/video/",
                               GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/right/annotation/",
                               GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "snapshot/right/motion/");
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method combinePNG {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    public boolean createVideo() throws Exception {
        try {
            long audioLength = 0;
            try {
                audioLength = ImageCommon.getLength(telestrationVideo.getSoundFilePath());
            } catch (Exception ex) {

            }

            if (audioLength == 0) {
                audioLength = (telestrationVideo.getTotalFrameOfVideo()/30) * 1000;
            }
//            long audioLength = ImageCommon.getLength("1.wav");
            double durationPerFrame = (double) audioLength / (double) telestrationVideo.getTotalFrameOfVideo();
            double fps = 1000 / durationPerFrame;

            List<String> command = new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-r");
            command.add(Double.toString(fps));
            command.add("-i");
            command.add(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder + "output/combine/%01d.png");
            command.add("-vf");
            command.add("scale=640:480");

            command.add("-pix_fmt");
            command.add("yuv420p");
            command.add(outPutFolder + "out.mp4");

            ProcessBuilder builder = new ProcessBuilder(command);
            final Process process = builder.start();

            InputStream is = process.getInputStream();
            String composite = "";
            String errorString = "";

            StreamBoozer sbin = new StreamBoozer(is);
            StreamBoozer sberr = new StreamBoozer(process.getErrorStream());
            sbin.start();
            sberr.start();
            process.waitFor();
            sbin.join();
            sberr.join();
            composite = sbin.output;
            errorString = sberr.output;
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method createVideo {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    public boolean attachAudio() throws Exception {
        try {
            List<String> command = new ArrayList<String>();
            command.add("ffmpeg");
            command.add("-i");
            command.add(outPutFolder + "out.mp4");
            command.add("-i");
            command.add(telestrationVideo.getSoundFilePath());
//            command.add("1.wav");
            command.add("-c:v");
            command.add("copy");
            command.add("-c:a");
            command.add("aac");
            command.add("-strict");
            command.add("experimental");
            command.add("-map");
            command.add("0:v:0");
            command.add("-map");
            command.add("1:a:0");
            command.add("-shortest");
            command.add(outPutFolder + "output.mp4");

            ProcessBuilder builder = new ProcessBuilder(command);
            final Process process = builder.start();

            InputStream is = process.getInputStream();
            String composite = "";
            String errorString = "";

            StreamBoozer sbin = new StreamBoozer(is);
            StreamBoozer sberr = new StreamBoozer(process.getErrorStream());
            sbin.start();
            sberr.start();
            process.waitFor();
            sbin.join();
            sberr.join();
            composite = sbin.output;
            errorString = sberr.output;
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method attachAudio {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }

    public boolean makeVideo() throws Exception {
        try {
            log.info("Making Video, {}", telestrationVideo.getTelestrationId());
            GTUtil.deleteFileSafely(outPutFolder + "out.mp4");
            GTUtil.deleteFileSafely(outPutFolder + "output.mp4");

            Files.createDirectories(Paths.get(outPutFolder));

            createVideo();
            log.info("Attach Audio, {}", telestrationVideo.getTelestrationId());
            attachAudio();

            log.info("Delete tmp folder, {}", telestrationVideo.getTelestrationId());

            GTUtil.deleteFileSafely(GTConstants.BASE_VIDEO_FACTORY_DIR + this.wrapperFolder);
            return true;
        } catch (Exception ex) {
            log.error("ERROR in method makeVideo {}, {}", ex.getMessage(), telestrationVideo.getTelestrationId());
            throw ex;
        }
    }
}
