package com.golftec.teaching.videoUtil.control;

import com.golftec.teaching.videoUtil.history.VideoData;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.videoUtil.util.VideoBoardEvent;
import com.golftec.teaching.videoUtil.util.VideoButtonType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.condition.Condition;
import uk.co.caprica.vlcj.player.condition.conditions.TimeReachedCondition;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class MediaPlayerControlForServer extends VBox implements MediaPlayerEventListener {

    private static final long TIME_SLOW_VALUE = 20;
    private static final long TIME_SUPPER_SLOW_VALUE = 10;
    public MediaPlayerCore mediaPlayerCore;
    public BorderPane mediaPlayerControlLayout;
    public VideoHistory videoHistory = null;
    public Pane videoPane;
    public AnchorPane ATIFpane;
    public ImageView keyFrameA;
    public ImageView keyFrameT;
    public ImageView keyFrameI;
    public ImageView keyFrameF;
    public double AShift = 0;
    public double TShift = 0;
    public double IShift = 0;
    public double FShift = 0;
    private IVideoEvent iVideoEvent = null;
    private final EventHandler<ActionEvent> nextFrameMotion = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            int frame = (int) (mediaPlayerCore.curTime / 33.3333);
            if (frame > 0) {
                if (iVideoEvent != null) {
                    iVideoEvent.frameChanged(frame);
                }
            }
        }
    };
    private IMediaAdapterPaused iMediaAdapterPaused = null;
    private IMediaAdapterPlayed iMediaAdapterPlayed = null;
    private Map imgList = null;
    private Map frameMap = null;
    private String exportFolder = "";
    private String filePath = "";
    private String sourceURL = "";
    private float curRate = 1;
    private float curPos = 0;
    private long curTime = 0;
    private long frame = 1; //for export
    private final EventHandler<ActionEvent> nextFrameExport = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
//            Image image = getSnapShot();
            try {
                frame++;
                long time = mediaPlayerCore.getTime();
//                System.out.println("Nexttime: " + time);
                long orginFrame = time / 30;
//                System.out.println("outFrame: " + frame + ", orginFrame: " + orginFrame);
                frameMap.put(frame, orginFrame);

//                System.out.println("adding video" + srcFrame + ".png to memory");

            } catch (Exception ex) {

            }

//            saveVideoImageIntoDisk(image, srcFrame);
//            imgList.put(srcFrame, image);
        }
    };

    private void saveVideoImageIntoDisk(Image image, long frame) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    File outputFile = new File(exportFolder + frame + ".png");
                    try {
                        System.out.println("Saving video" + frame + ".png to disk");
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
                    } catch (Exception s) {
                        System.out.println(s);
                    }
                });
            }
        };
        timerTask.run();
    }

    private double totalTime = 0; // in miliseconds
    private int shiftPx = -20;
    private int totalFrames = 0;
    private int FPS = 30;
    private float stepDelta = 0.1f; // for jump back and jump forward
    private Timeline timeline;
    private Timeline timelineExport;
    private Timeline motionTimeline;
    private long slowDeltaTime = 0; // for rewind
    private boolean isDisposed = false;
    private VideoStatus status = VideoStatus.NOT_STARTED; // status of player
    private boolean isStarted = false;
    private Integer AFrame = null;
    private Integer TFrame = null;
    private Integer IFrame = null;

    // 0: Slow back
    // 1: Super Slow Back
    // 2: Slow Forward
    // 3: Super Slow Forward
    private Integer FFrame = null;
    private byte timeLineStatus = 0; // need this variable cause the status is not set correctly in time, cause it's set on another thread
    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            processSlowDown();
        }
    };
    private boolean isRecording = false;

    public void clearImage() {
        imgList = null;
    }

    public MediaPlayerControlForServer(String filePath, boolean useUI) {
        this.filePath = filePath;

        if (useUI) {
            initUI();
        } else {
            initOwnUI();
        }

        init();
    }

    public MediaPlayerControlForServer(String filePath, boolean useUI, IVideoEvent iVideoEvent) {
        this.iVideoEvent = iVideoEvent;
        this.filePath = filePath;
        if (useUI) {
            initUI();
        } else {
            initOwnUI();
        }

        init();
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public void startRecord() {
        this.videoHistory.startRecord();
        this.isRecording = true;
    }

    public boolean getHorizontalFlip() {
        return this.mediaPlayerCore.getHorizonalFlip();
    }

    public void setHorizontalFlip(boolean horizontalFlip) {
        this.mediaPlayerCore.setHorizonalFlip(horizontalFlip);
    }

    public void stopRecord() {
//        this.isRecording = false;
//        this.videoHistory.serialize();
//        this.videoHistory.stopRecord();
    }

    public VideoHistory getVideoHistory() {
        return this.videoHistory;
    }

    public void setVideoHistory(VideoHistory videoHistory) {
        this.videoHistory = videoHistory;
    }

    public void setAFrame(int frame) {
        this.AFrame = frame;
    }

    public void setTFrame(int frame) {
        this.TFrame = frame;
    }

    public void setIFrame(int frame) {
        this.IFrame = frame;
    }

    public void setFFrame(int frame) {
        this.FFrame = frame;
    }

    public long getFrame() {
        try {
            long frame = 0;
            long time = this.mediaPlayerCore.getTime();
            frame = (long) (time * this.totalFrames / this.totalTime);
            return frame;
        } catch (Exception ex) {

        }
        return 0;
    }

    public void setFrame(long frame) {
        try {
            double time = frame * this.totalTime / this.totalFrames;
//            mediaPlayerCore.setTime((long) time);

            System.out.println("before: " + mediaPlayerCore.getTime());
            Condition<?> timeReachedCondition = new TimeReachedCondition(mediaPlayerCore.getMediaPlayer(), (long) time) {
                @Override
                protected boolean onBefore() {
                    mediaPlayer.setTime(targetTime);
                    return true;
                }
            };
            timeReachedCondition.await();
            System.out.println("after: " + mediaPlayerCore.getTime());
        } catch (Exception ex) {
        }
    }

    private void initOwnUI() {

    }

    private void initUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mediaplayer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void setFilePath(String filePath) {
        VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.SET_PATH);
        videoBoardEvent.filePath = filePath;
        consumeEvent(videoBoardEvent);
    }

    public void dispose() {
        try {
            if (!isDisposed) {
                timeline.stop();
                timelineExport.stop();
                motionTimeline.stop();
                mediaPlayerCore.dispose();
                isDisposed = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startPlay() {
        try {
            System.out.println("Start Playing");
            mediaPlayerCore.play();

            status = VideoStatus.IS_PLAYING;
        } catch (Exception ex) {
        }
    }

    public boolean isStarted() {
        return isStarted;
    }

    //change the pause mode back to play mode
    private void resume() {
        try {
            status = VideoStatus.IS_PLAYING;
//            if (!mediaPlayerCore.isPlaying()) {
            timeline.stop();
            mediaPlayerCore.setPause(false);
//            }
        } catch (Exception ex) {

        }
    }

    // pause the player
    private void pause() {
        try {
            timeline.stop();
            mediaPlayerCore.setPause(true);
        } catch (Exception ex) {

        }
    }

    public void resetMedia() {
        this.status = VideoStatus.NOT_STARTED;
//        timeline.stop();
//        timelineExport.stop();
//        motionTimeline.stop();
    }

    public void consumeEvent(VideoBoardEvent videoBoardEvent) {

        switch (videoBoardEvent.videoButtonType) {
            case SET_PATH: {
                try {
                    resetMedia();
                    this.sourceURL = videoBoardEvent.sourceURL;
                    mediaPlayerCore.stop();
                    mediaPlayerCore.setFilePath(videoBoardEvent.filePath);
                } catch (Exception ex) {

                }
                break;
            }

            case FLIP: {
                try {
                    boolean val = !this.getHorizontalFlip();
                    this.mediaPlayerCore.setHorizonalFlip(val);
                } catch (Exception ex) {
                }
                break;
            }

            case A: {
                try {
                    setFrame((long) videoBoardEvent.value);
                } catch (Exception ex) {
                }
                break;
            }

            case T: {
                try {
                    setFrame((long) videoBoardEvent.value);
                } catch (Exception ex) {
                }
                break;
            }

            case I: {
                try {
                    setFrame((long) videoBoardEvent.value);
                } catch (Exception ex) {
                }
                break;
            }

            case F: {
                try {
                    setFrame((long) videoBoardEvent.value);
                } catch (Exception ex) {
                }
                break;
            }

            case SCRUB_MOUSE_CLICKED: {
                // in case of the video is playing so that when release the scrub bar we need to resume the video
                if (status == VideoStatus.IS_PLAYING) {
                    resume();
                }

                break;
            }

            case SCRUB_MOUSE_DRAGGED: {
                try {
                    timeline.pause();
                    mediaPlayerCore.setPause(true);
                } catch (Exception ex) {

                }
                break;
            }

            case SCRUB_CHANGE: {
                try {
                    curPos = videoBoardEvent.value;
                    curTime = mediaPlayerCore.getTime();

                    mediaPlayerCore.setPosition(curPos);
                } catch (Exception ex) {

                }
                break;
            }

            case STOP: {
                pause();
                break;
            }

            case PLAY: {
                playNormally();
                break;
            }

            case NOT_STARTED: {
                mediaPlayerCore.setFilePath(videoBoardEvent.filePath);
                break;
            }

            case START_PLAY: {
                startPlay();
                break;
            }

            case SET_SOURCE_URL: {
                try {
                    resetMedia();
                    this.sourceURL = videoBoardEvent.sourceURL;
                    mediaPlayerCore.stop();
                    mediaPlayerCore.setFilePath(videoBoardEvent.filePath);
                } catch (Exception ex) {

                }
                break;
            }

            case PLAY_BUTTON: {
                try {
                    switch (status) {
                        case NOT_STARTED: {
                            startPlay();
                            break;
                        }
                        case IS_PLAYING: {
                            pause();
                            break;
                        }
                        case IS_SLOWING_BACK:
                        case IS_SUPER_SLOW_BACK:
                        case IS_SLOWING_FORWARD:
                        case IS_SUPER_SLOW_FORWARD: {
                            playNormally();
                            break;
                        }
                        case IS_ENDSONG: {
                            startPlay();
                            break;
                        }
                        case IS_PAUSING: {
                            resume();
                            break;
                        }
                    }
                } catch (Exception ex) {
                }
                break;
            }

            case SLOW_BACK: {
                try {
                    timeLineStatus = 0;
                    pause();
                    status = VideoStatus.IS_SLOWING_BACK;
                    slowDeltaTime = TIME_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }

            case SLOW_FORWARD: {
                try {
                    timeLineStatus = 2;
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SLOWING_FORWARD;
                    slowDeltaTime = TIME_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }

            case STEP_BACK: {
                try {
                    curPos = mediaPlayerCore.getPosition();
                    curPos = curPos - stepDelta;
                    mediaPlayerCore.setPosition(curPos);
                    pause();
                } catch (Exception ex) {
                }
                break;
            }

            case STEP_FORWARD: {
                try {
                    curPos = mediaPlayerCore.getPosition();
                    curPos = curPos + stepDelta;
                    mediaPlayerCore.setPosition(curPos);
                    pause();
                } catch (Exception ex) {
                }
                break;
            }

            case SUPER_SLOW_BACK: {
                try {
                    timeLineStatus = 1;
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SUPER_SLOW_BACK;
                    slowDeltaTime = TIME_SUPPER_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }

            case SUPER_SLOW_FORWARD: {
                try {
                    timeLineStatus = 3;
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SUPER_SLOW_FORWARD;
                    slowDeltaTime = TIME_SUPPER_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }
        }
    }

    public void showHistory(VideoHistory videoHistory) {
        this.videoHistory = videoHistory;
        this.videoHistory.showHistory(this);
    }

    public BorderPane getVideoPane() {
        return mediaPlayerCore.getBorderPane();
    }

    public void setVideoPane(Pane pane) {
        this.videoPane = pane;
        if (videoPane != null) {
            BorderPane borderPane = mediaPlayerCore.getBorderPane();
            videoPane.getChildren().add(0, borderPane);
        }
    }

    public void finish() {
        this.videoHistory.finish();
    }

    public void consumeStandByEvent(VideoData videoData) {

        System.out.println(videoData.getStatus());

        if (this.filePath != videoData.getFilePath()) {
            this.mediaPlayerCore.setFilePath(videoData.getFilePath());
        }

        if (videoData.getStatus() == VideoStatus.IS_FINISH) {
            dispose();
            return;
        }

        if (!isStarted) {
            iMediaAdapterPlayed = new IMediaAdapterPlayed() {
                @Override
                public void played() {
                    iMediaAdapterPaused = new IMediaAdapterPaused() {
                        @Override
                        public void paused() {
                            long frame = videoData.getFrame();

                            setFrame(frame);

                            iMediaAdapterPaused = new IMediaAdapterPaused() {
                                @Override
                                public void paused() {

                                }
                            };

                            iMediaAdapterPlayed = new IMediaAdapterPlayed() {
                                @Override
                                public void played() {

                                }
                            };

                            System.out.println("videoData.getStatus(): " + videoData.getStatus());

                            switch (videoData.getStatus()) {
                                case IS_PAUSING: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.STOP));
                                    break;
                                }
                                case IS_SLOWING_BACK: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_BACK));
                                    break;
                                }
                                case IS_SLOWING_FORWARD: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_FORWARD));
                                    break;
                                }
                                case IS_SUPER_SLOW_BACK: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_BACK));
                                    break;
                                }
                                case IS_SUPER_SLOW_FORWARD: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_FORWARD));
                                    break;
                                }
                                case IS_PLAYING: {
                                    consumeEvent(new VideoBoardEvent(VideoButtonType.PLAY));
                                    break;
                                }
                                case IS_ENDSONG: {
                                    try {
                                    } catch (Exception ex) {

                                    }

                                    break;
                                }
                            }
                        }
                    };
                    pause();
                }
            };
            startPlay();
        }
    }

    public void cancelShowingHistory() {
        this.videoHistory.cancelShowHistory();
    }

    public void standByHistory() {
        this.videoHistory.pause();
    }

    public void continueHistory() {
        this.videoHistory.resumeHistory();
    }

    public void addStandByHistory() {
        VideoData videoData = new VideoData();
        videoData.setFrame(getFrame());
        videoData.setStatus(status);
        videoData.setSourceURL(this.sourceURL);
        videoData.setFilePath(this.filePath);

        videoHistory.addStandby(videoData);
    }

    public void stopExportImage() {
        timelineExport.stop();
    }

    public void startExportImage(String exportFolder) {
        this.exportFolder = exportFolder;
        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }
        timelineExport.playFromStart();
    }

    public void init() {
        imgList = new HashMap();
        frameMap = new HashMap();
        videoHistory = new VideoHistory();
        mediaPlayerCore = new MediaPlayerCore(this.filePath, this);
        BorderPane borderPane = mediaPlayerCore.getBorderPane();

        if (videoPane != null) {
            videoPane.getChildren().add(0, borderPane);
        }

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        double duration = 1000 / FPS;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));

        motionTimeline = new Timeline();
        motionTimeline.setCycleCount(Timeline.INDEFINITE);
        motionTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrameMotion));
        motionTimeline.playFromStart();

        timelineExport = new Timeline();
        timelineExport.setCycleCount(Timeline.INDEFINITE);
        timelineExport.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrameExport));
    }

    private void playNormally() {
        timeline.stop();
        curRate = 1f;
        mediaPlayerCore.setRate(curRate);
        resume();
    }

    public Image getSnapShot() {
        Pane pane = this.mediaPlayerCore.getBorderPane();
        double width = pane.getWidth();
        double height = pane.getHeight();

        if (width == 0) {
            width = 315;
        }
        if (height == 0) {
            height = 478;
        }

        WritableImage wImage = new WritableImage((int) width, (int) height);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        wImage = pane.snapshot(sp, null);

        return wImage;
    }

    public void saveSnapshotIntoDisk() {
        stopExportImage();

        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }

        for (long frame = 1; frame < imgList.size(); frame++) {
            WritableImage image = (WritableImage) imgList.get(frame);
            File outputFile = new File(exportFolder + frame + ".png");

            try {
                System.out.println("Saving video" + frame + ".png to disk");
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }

        imgList = null;
    }

    public void saveSnapshotIntoDiskEx() {
        stopExportImage();

        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }

        for (long frame = 1; frame < imgList.size(); frame++) {
            WritableImage image = (WritableImage) imgList.get(frame);
            File outputFile = new File(exportFolder + frame + ".png");

            try {
                System.out.println("Saving video" + frame + ".png to disk");
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }

        imgList = null;
    }

    private void processSlowDown() {
        try {
            this.mediaPlayerCore.setPause(true);

            curTime = this.mediaPlayerCore.getTime();

            if (curTime <= 0 || curTime >= totalTime) {
                return;
            }
            switch (timeLineStatus) {
                case 0:
                case 1: {
                    curTime = curTime - slowDeltaTime;
                    break;
                }
                case 2:
                case 3: {
                    curTime = curTime + slowDeltaTime;
                    break;
                }
            }
            mediaPlayerCore.setTime(curTime);

            // this is needed because if we don't restrict the scrub bar update value then the video is frozen
//            if (Math.abs(newPos - curPos) > 0.0005) {
//                curPos = newPos;
//            }

        } catch (Exception ex) {

        }
    }

    //play from begining

    @Override
    public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t libvlc_media_t, String s) {
        System.out.println("mediaChanged");
    }

    @Override
    public void opening(MediaPlayer mediaPlayer) {
        System.out.println("opening");
    }

    @Override
    public void buffering(MediaPlayer mediaPlayer, float v) {
//        System.out.println("buffering");
    }

    @Override
    public void playing(MediaPlayer mediaPlayer) {
        try {
            isStarted = true;
            System.out.println("playing");
            if (this.iVideoEvent != null) {
                this.iVideoEvent.started();
            }
            try {
                this.totalTime = mediaPlayerCore.getTotalTimeByMillisecond();
                this.totalFrames = (int) (this.totalTime / 1000 * FPS);
            } catch (Exception ex) {

            }

            if (iMediaAdapterPlayed != null) {
                iMediaAdapterPlayed.played();
            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void paused(MediaPlayer mediaPlayer) {
        System.out.println("paused");
        status = VideoStatus.IS_PAUSING;
        if (iMediaAdapterPaused != null) {
            iMediaAdapterPaused.paused();
        }
    }

    @Override
    public void stopped(MediaPlayer mediaPlayer) {
        System.out.println("stopped");
    }

    @Override
    public void forward(MediaPlayer mediaPlayer) {
        System.out.println("forward");
    }

    @Override
    public void backward(MediaPlayer mediaPlayer) {
        System.out.println("backward");
    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
        System.out.println("finished");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                timeline.stop();
                status = VideoStatus.IS_ENDSONG;
            }
        });
    }

    @Override
    public void timeChanged(MediaPlayer mediaPlayer, long l) {
        mediaPlayerCore.curTime = l;
    }

    @Override
    public void positionChanged(MediaPlayer mediaPlayer, float v) {
        try {
            curPos = v;
            curTime = this.mediaPlayerCore.getTime();
        } catch (Exception ex) {

        }
    }

    @Override
    public void seekableChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("seekableChanged");
    }

    @Override
    public void pausableChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("pausableChanged");
    }

    @Override
    public void titleChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("titleChanged");
    }

    @Override
    public void snapshotTaken(MediaPlayer mediaPlayer, String s) {
        System.out.println("snapshotTaken");
    }

    @Override
    public void lengthChanged(MediaPlayer mediaPlayer, long l) {
        System.out.println("lengthChanged");
    }

    @Override
    public void videoOutput(MediaPlayer mediaPlayer, int i) {
        System.out.println("videoOutput");
    }

    @Override
    public void scrambledChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("scrambledChanged");
    }

    @Override
    public void elementaryStreamAdded(MediaPlayer mediaPlayer, int i, int i1) {
        System.out.println("elementaryStreamAdded");
    }

    @Override
    public void elementaryStreamDeleted(MediaPlayer mediaPlayer, int i, int i1) {
        System.out.println("elementaryStreamDeleted");
    }

    @Override
    public void elementaryStreamSelected(MediaPlayer mediaPlayer, int i, int i1) {
        System.out.println("elementaryStreamSelected");
    }

    @Override
    public void error(MediaPlayer mediaPlayer) {
        System.out.println("error");
    }

    @Override
    public void mediaMetaChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("mediaMetaChanged");
    }

    @Override
    public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t libvlc_media_t) {
        System.out.println("mediaSubItemAdded");
    }

    @Override
    public void mediaDurationChanged(MediaPlayer mediaPlayer, long l) {
        System.out.println("mediaDurationChanged");
    }

    @Override
    public void mediaParsedChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("mediaParsedChanged");
    }

    @Override
    public void mediaFreed(MediaPlayer mediaPlayer) {
        System.out.println("mediaFreed");
    }

    @Override
    public void mediaStateChanged(MediaPlayer mediaPlayer, int i) {
        System.out.println("mediaStateChanged");
    }



    @Override
    public void newMedia(MediaPlayer mediaPlayer) {
        System.out.println("newMedia");
    }

    @Override
    public void subItemPlayed(MediaPlayer mediaPlayer, int i) {
        System.out.println("subItemPlayed");
    }

    @Override
    public void subItemFinished(MediaPlayer mediaPlayer, int i) {
        System.out.println("subItemFinished");
    }

    @Override
    public void endOfSubItems(MediaPlayer mediaPlayer) {
        System.out.println("endOfSubItems");
    }
}
