package com.golftec.teaching.videoUtil.util;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.google.common.collect.Maps;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by Teo on 2015-07-10.
 */
public class VideoRender {

    private static final Logger log = LoggerFactory.getLogger(VideoRender.class);

    private VideoHistory videoHistory = null;

    // folder to put the snapshot into
    private String snapshotVideoFolder = "snapshot/left/video/";

    // folder to put PNG of source Video
    private String sourcePNGFolder = GTConstants.BASE_VIDEO_FACTORY_DIR + "sourceVideoPNG/";

    // folder to download video into
    private String downloadFolder = GTConstants.BASE_VIDEO_FACTORY_DIR + "/temp/";

    private Map<String, Integer> sourceVideoList = Maps.newHashMap();

    private int numberOfThreads = 7;

    public VideoRender() {
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void setDownloadFolder(String folder) {
        this.downloadFolder = folder;
    }

    public void setPNGFolder(String folder) {
        this.sourcePNGFolder = folder;
    }

    public String getSnapshotVideoFolder() {
        return this.snapshotVideoFolder;
    }

    public void setSnapshotVideoFolder(String folder) {
        this.snapshotVideoFolder = folder;
    }

    private void makeSimulateData() {
        FrameData frameData = new FrameData();
        frameData.filePath = "";
        frameData.desFrame = 1;
        frameData.srcFrame = 1;
        frameData.sourceURL = "http://45.33.66.59:40003/pro-videos/afbedbdd8087c00a425b14383cc5cfa3/resized_320x480.mp4";

        videoHistory.videoFrameMap.put(frameData.desFrame, frameData);

        frameData = new FrameData();
        frameData.filePath = "";
        frameData.desFrame = 2;
        frameData.srcFrame = 2;
        frameData.sourceURL = "http://45.33.66.59:40003/pro-videos/afbedbdd8087c00a425b14383cc5cfa3/resized_320x480.mp4";

        videoHistory.videoFrameMap.put(frameData.desFrame, frameData);

        frameData = new FrameData();
        frameData.filePath = "";
        frameData.desFrame = 3;
        frameData.srcFrame = 3;
        frameData.sourceURL = "http://45.33.66.59:40003/pro-videos/afbedbdd8087c00a425b14383cc5cfa3/resized_320x480.mp4";

        videoHistory.videoFrameMap.put(frameData.desFrame, frameData);

        frameData = new FrameData();
        frameData.filePath = "";
        frameData.desFrame = 4;
        frameData.srcFrame = 4;
        frameData.sourceURL = "http://45.33.66.59:40003/pro-videos/ab95641d8180c57388db94260068a696/resized_320x480.mp4";

        videoHistory.videoFrameMap.put(frameData.desFrame, frameData);
    }

    public VideoHistory getVideoHistory() {
        return this.videoHistory;
    }

    public void setVideoHistory(VideoHistory videoHistory) {
        this.videoHistory = videoHistory;
    }

    public void start(boolean useSourceURL) throws Exception {
//        makeSimulateData();
        try {
            if (videoHistory == null) {
                System.out.println("No Video History");
                return;
            }
            if (useSourceURL) {
                downloadAndSetFilePath();
            }

            createPNGImages();
            makeFrameImagesForOutPutVideo();
        } catch (Exception ex) {
            throw ex;
        }
    }

    private boolean isEmptyFrame(FrameData frameData) {
        if ("".equals(frameData.filePath)
                || frameData.srcFrame == 0
                || frameData.desFrame == 0) {
            return true;
        }
        return false;
    }

    public void downloadAndSetFilePath() throws Exception {
        try {
            GTUtil.deleteFileSafely(Paths.get(downloadFolder));
            Files.createDirectories(Paths.get(downloadFolder));
        } catch (Exception ex) {
            throw ex;
        }

        if (this.videoHistory.videoFrameMap != null) {
            Map<String, String> tempMap = Maps.newHashMap(); // contain sourceURL match with local File

            Map map = this.videoHistory.videoFrameMap;
            Iterator it = map.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();

                FrameData frameData = (FrameData) pair.getValue();

                // check if not download then download
                String path = "";
                if (!frameData.sourceURL.equals("")) {
                    String value = tempMap.get(frameData.sourceURL);
                    if (value == null) {
                        path = downloadFolder + GTUtil.uuid() + ".mp4";
                        File file = new File(path);
                        try {
                            URL url = new URL(frameData.sourceURL);
//                            URL url = new URL("http://54.197.238.74:50003/pro-videos/3f2dbf42f79f119d9e6517f46b52b2b8/resized_320x480.mp4");
                            System.out.println("downloading video file: " + url);
                            System.out.println("save to: " + path);
                            FileUtils.copyURLToFile(url, file);

                            tempMap.put(frameData.sourceURL, path);
                        } catch (Exception ex) {
                            path = "";
                        }
                    } else {
                        path = value;
                    }
                }

                //set filePath
                frameData.filePath = path;
            }
        }
    }

    private void createEmptyImage() throws Exception {
        try {
            BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            File file = new File(sourcePNGFolder + "empty.png");
            ImageIO.write(bufferedImage, "PNG", file);
        } catch (Exception ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    private void makePNGByFrame(FrameData frameData) {
        if (frameData != null) {
            try {
                if (isEmptyFrame(frameData)) {
                    String srcFolder = sourcePNGFolder;
                    String srcFile = "empty.png";
                    String desFolder = snapshotVideoFolder;
                    String desFile = frameData.desFrame + ".png";
                    copyPNG(srcFolder, srcFile, desFolder, desFile);
                } else {
                    String srcFolder = sourcePNGFolder + Common.getFileNameFromPath(frameData.filePath) + File.separator;
                    int frames = sourceVideoList.get(frameData.filePath);
                    if (frameData.srcFrame > frames) {
                        frameData.srcFrame = frames;
                    }
                    String srcFile = "frame_" + frameData.srcFrame + ".png";
                    String desFolder = snapshotVideoFolder;
                    String desFile = frameData.desFrame + ".png";
                    copyPNG(srcFolder, srcFile, desFolder, desFile);
                    if (frameData.isFlip) {
                        Common.flipImage(desFolder + desFile);
                    }
                }
            } catch (Exception ex) {
                System.out.println("makeFrameImagesForOutPutVideo, desFrame: " + frameData.desFrame);
            }
        }
    }

    private void makeFrameImagesForOutPutVideo() throws Exception {
        if (this.videoHistory != null) {
            if (this.videoHistory.videoFrameMap != null) {
                try {
                    GTUtil.deleteFileSafely(Paths.get(snapshotVideoFolder));
                    Files.createDirectories(Paths.get(snapshotVideoFolder));
                } catch (Exception ex) {
                }

                createEmptyImage();

                Map map = this.videoHistory.videoFrameMap;
                Iterator it = map.entrySet().iterator();

                BlockingQueue<FrameData> blockingDeque =
                        new ArrayBlockingQueue<>(map.size());

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    FrameData frameData = (FrameData) pair.getValue();
                    blockingDeque.add(frameData);
//                    makePNGByFrame(frameData);
                }

                java.util.List<Thread> threads = new ArrayList<>();

                for (int i = 0; i < numberOfThreads; i++) {
                    Thread thread = new Thread() {
                        public void run() {
                            FrameData frameData = null;
                            while ((frameData = blockingDeque.poll()) != null) {
                                makePNGByFrame(frameData);
                            }
                        }
                    };
                    threads.add(thread);
                    thread.start();
                }

                for (int i = 0; i < threads.size(); i++) {
                    Thread thread = threads.get(i);
                    thread.join();
                }
            }
        }
    }

    //download video base on sourceURL, save the video into local folder
    // then set filePath to that folder

    public void createPNGImages() throws Exception {
        try {
            GTUtil.deleteFileSafely(Paths.get(sourcePNGFolder));
            Files.createDirectories(Paths.get(sourcePNGFolder));
        } catch (Exception ex) {
        }

        if (this.videoHistory != null) {
            if (this.videoHistory.videoFrameMap != null) {
                Map<String, String> tempMap = Maps.newHashMap(); // contain sourceURL match with local File

                Map map = this.videoHistory.videoFrameMap;
                Iterator it = map.entrySet().iterator();

                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();

                    FrameData frameData = (FrameData) pair.getValue();
                    String videoPath = frameData.filePath;
                    if (!videoPath.trim().equals("")) {
                        // check if not download then download
                        String value = tempMap.get(videoPath);
                        if (value == null) {
                            try {
                                int fps = ImageCommon.getFps(videoPath);
                                String desFolder = sourcePNGFolder + Common.getFileNameFromPath(videoPath);

                                // create destination folder
                                GTUtil.deleteFileSafely(Paths.get(desFolder));
                                Files.createDirectories(Paths.get(desFolder));

                                log.info("Making PNG Images from file: {} to folder {}", videoPath, desFolder);
                                ImageCommon.convertMp4ToPng(videoPath, fps, desFolder, "frame");
                                log.info("Done making PNG Images from file: {} to folder {}", videoPath, desFolder);

                                sourceVideoList.put(videoPath, Common.getTotalFiles(desFolder));
                            } catch (Exception ex) {
                                System.out.println("ERROR when making PNG Images from file: " + videoPath);
                            }
                            tempMap.put(frameData.filePath, frameData.filePath);
                        }
                    }
                }
            }
        }
    }

    public void copyPNG(String srcFolder, String srcFile, String desFolder, String desFile) throws Exception {
        try {
            File src = new File(srcFolder + srcFile);
            File des = new File(desFolder + desFile);
//            System.out.println("Copying file. Source file: " + srcFolder + srcFile + ", Destination file: " + desFolder + desFile);
            FileUtils.copyFile(src, des);
        } catch (Exception ex) {
            System.out.println("Cannot copy file. Source file: " + srcFolder + srcFile + ", Destination file: " + desFolder + desFile);
            throw ex;
        }
    }

    public void makePNG(long frame, String videoPath, String folder, String fileName) throws Exception {
        try {
            String cmd = "ffmpeg  -i test.mp4 -vf \"select=gte<n\\,100>\" -vframes 1 out_img.png";

            Process pro = Runtime.getRuntime().exec(cmd);
            pro.waitFor(10000, TimeUnit.MILLISECONDS);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
