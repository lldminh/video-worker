package com.golftec.teaching.videoUtil.control;

import com.golftec.teaching.videoUtil.history.VideoData;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.videoUtil.util.VideoBoardEvent;
import com.golftec.teaching.videoUtil.util.VideoButtonType;
import com.golftec.teaching.videoUtil.motion.MotionDataBoard;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import com.golftec.teaching.videoUtil.util.Common;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.condition.Condition;
import uk.co.caprica.vlcj.player.condition.conditions.TimeReachedCondition;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class MediaPlayerControl extends VBox implements MediaPlayerEventListener {

    public Button btnPlay;
    public Pane videoPane;
    public Button btnStepb;
    public Button btnStepf;
    public Label lblTime;
    public Button btnRewindSlow;
    public Button btnForwardSlow;
    public Button btnRewindSupperSlow;
    public Button btnForwardSupperSlow;
    public MediaPlayerCore mediaPlayerCore;
    public BorderPane mediaPlayerControlLayout;
    public Slider scrubBar;

    public AnchorPane ATIFpane;
    public ImageView keyFrameA;
    public ImageView keyFrameT;
    public ImageView keyFrameI;
    public ImageView keyFrameF;

    public VideoHistory videoHistory = null;

    public MotionDataBoard motionDataBoard = null;

    private IVideoEvent iVideoEvent = null;

    private Map imgList = null;

    private String filePath = "";
    private String sourceURL = "";
    private float curRate = 1;
    private float curPos = 0;
    private long curTime = 0;
    private long frame = 0; //for export
    private double totalTime = 0; // in miliseconds

    private int shiftPx = -20;

    private int totalFrames = 0;

    private int FPS = 30;

    private boolean flag = false; // user interact

    private float stepDelta = 0.1f; // for jump back and jump forward

    private String exportFolder = "";

    private Timeline timeline;
    private Timeline timelineExport;
    private Timeline motionTimeline;

    private long slowDeltaTime = 0; // for rewind

    private static final long TIME_SLOW_VALUE = 20;
    private static final long TIME_SUPPER_SLOW_VALUE = 10;

    private VideoStatus status = VideoStatus.NOT_STARTED; // status of player
    private boolean isStarted = false;



    private Integer AFrame = null;
    private Integer TFrame = null;
    private Integer IFrame = null;
    private Integer FFrame = null;

    public double AShift = 0;
    public double TShift = 0;
    public double IShift = 0;
    public double FShift = 0;

    private boolean isRecording = false;

    public void startRecord() {
        this.videoHistory.startRecord();
        this.isRecording = true;
    }

    public void stopRecordAndSave(String filePath, String fileName) {
        this.isRecording = false;
        this.videoHistory.stopRecord();
        this.videoHistory.serialize(filePath, fileName);
    }

    public void setHorizontalFlip(boolean horizontalFlip) {

        this.mediaPlayerCore.setHorizonalFlip(horizontalFlip);
    }

    public String getSourceURL() {
        return this.sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public boolean getHorizontalFlip() {
        return this.mediaPlayerCore.getHorizonalFlip();
    }

    public void setVideoHistory(VideoHistory videoHistory) {
        this.videoHistory = videoHistory;
    }

    public VideoHistory getVideoHistory() {
        return this.videoHistory;
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

    public void setFrame(long frame) {
        try {
            flag = true;
            double time = frame * this.totalTime / this.totalFrames;
            mediaPlayerCore.setTime((long) time);
            scrubBar.setValue(mediaPlayerCore.getPosition());
            curPos = (float) scrubBar.getValue();
        } catch (Exception ex) {

        }
    }

    private void initOwnUI() {
        btnPlay = new Button();
        videoPane = new Pane();
        btnStepb = new Button();
        btnStepf = new Button();
        lblTime = new Label();
        btnRewindSlow = new Button();
        btnForwardSlow = new Button();
        btnRewindSupperSlow = new Button();
        btnForwardSupperSlow = new Button();
        mediaPlayerControlLayout = new BorderPane();
        scrubBar = new Slider();
        ATIFpane = new AnchorPane();
        keyFrameA = new ImageView();
        keyFrameT = new ImageView();
        keyFrameI = new ImageView();
        keyFrameF = new ImageView();
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

    public MediaPlayerControl(String filePath, boolean useUI) {
        this.filePath = filePath;

        if (useUI) {
            initUI();
        } else {
            initOwnUI();
        }

        init();
    }

    public MediaPlayerControl(String filePath, boolean useUI, IVideoEvent iVideoEvent) {
        this.iVideoEvent = iVideoEvent;
        this.filePath = filePath;
        if (useUI) {
            initUI();
        } else {
            initOwnUI();
        }

        init();
    }

    public void setFilePath(String filePath) {
        VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.SET_PATH);
        videoBoardEvent.filePath = filePath;
        consumeEvent(videoBoardEvent);
    }

    public BorderPane getVideoPane() {
        return this.mediaPlayerCore.getBorderPane();
    }

    public void dispose() {
        try {
            timeline.stop();
            timelineExport.stop();
            motionTimeline.stop();
            mediaPlayerCore.dispose();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void showATIF() {
        final double barWidth = this.scrubBar.getWidth();
        final double pxA = barWidth * this.AFrame / this.totalFrames;
        final double pxT = barWidth * this.TFrame / this.totalFrames;
        final double pxI = barWidth * this.IFrame / this.totalFrames;
        final double pxF = barWidth * this.FFrame / this.totalFrames;

        double b = 5.5;

        AShift = b - ((pxA / barWidth * 10));
        TShift = b - ((pxT / barWidth * 10));
        FShift = b - ((pxF / barWidth * 10));
        IShift = b - ((pxI / barWidth * 10));

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                keyFrameA.setLayoutX(pxA + shiftPx + AShift);
                keyFrameA.setVisible(true);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                keyFrameT.setLayoutX(pxT + shiftPx + TShift);
                keyFrameT.setVisible(true);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                keyFrameI.setLayoutX(pxI + shiftPx + IShift);
                keyFrameI.setVisible(true);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                keyFrameF.setLayoutX(pxF + shiftPx + FShift);
                keyFrameF.setVisible(true);
            }
        });
    }

    private void updateTimeLabel() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String time = Common.getTime(curTime);
                time = time + "/" + Common.formatTotalTime(totalTime);
                lblTime.setText(time);
            }
        });
    }

    private void startPlay() {
        try {
            switchPlayButton(true);
            mediaPlayerCore.play();

            status = VideoStatus.IS_PLAYING;
        } catch (Exception ex) {
        }
    }

    //change the pause mode back to play mode
    private void resume() {
        try {
            status = VideoStatus.IS_PLAYING;
            if (!mediaPlayerCore.isPlaying()) {
                timeline.stop();
                mediaPlayerCore.setPause(false);
            }
        } catch (Exception ex) {

        }
    }

    // pause the player
    private void pause() {
        try {
            timeline.stop();
            switchPlayButton(false);
            mediaPlayerCore.setPause(true);
            status = VideoStatus.IS_PAUSING;
        } catch (Exception ex) {

        }
    }

    public void resetMedia() {
        switchPlayButton(false);
        this.status = VideoStatus.NOT_STARTED;
//        timeline.stop();
//        timelineExport.stop();
//        motionTimeline.stop();
    }

    public void consumeEvent(VideoBoardEvent videoBoardEvent) {

        System.out.println(videoBoardEvent.videoButtonType);
        if (isRecording) {
            if (videoBoardEvent.videoButtonType == VideoButtonType.SCRUB_CHANGE) {
                if (flag) {
                    videoHistory.add(videoBoardEvent);
                }
            } else {
                videoHistory.add(videoBoardEvent);
            }
        }

        switch (videoBoardEvent.videoButtonType) {
            case SET_PATH: {
                try {
                    resetMedia();
//                    mediaPlayerCore.stop();
//                    mediaPlayerCore.dispose();
                    this.filePath = videoBoardEvent.filePath;
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
                if (scrubBar != null) {
                    float value = (float) scrubBar.getValue();
                    mediaPlayerCore.setPosition(value);
//                    if (!mediaPlayerCore.isPlaying()) {
//                        timeline.play();
//                    }

                    // in case of the video is playing so that when release the scrub bar we need to resume the video
                    if (status == VideoStatus.IS_PLAYING) {
                        resume();
                    }
                }

                break;
            }
            case SCRUB_MOUSE_DRAG_ENTERED: {
                float value = (float) scrubBar.getValue();
                mediaPlayerCore.setPosition(value);
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
                    updateTimeLabel();

                    if (flag) {
                        System.out.println("isDraging");
                        scrubBar.setValue(curPos);
                        mediaPlayerCore.setPosition(curPos);
                    }
                    flag = false;
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

            case SET_SOURCE_URL: {
                try {
                    resetMedia();
                    this.sourceURL = videoBoardEvent.sourceURL;
//                    mediaPlayerCore.stop();
//                    mediaPlayerCore.dispose();
                    mediaPlayerCore.setFilePath(videoBoardEvent.filePath);
//                    mediaPlayerCore = new MediaPlayerCore(videoBoardEvent.filePath, this);
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
                        case IS_SUPER_SLOW_BACK:
                        case IS_SUPER_SLOW_FORWARD:
                        case IS_SLOWING_BACK:
                        case IS_SLOWING_FORWARD: {
                            switchPlayButton(true);
                            playNormally();
                            break;
                        }
                        case IS_ENDSONG: {
                            startPlay();
                            break;
                        }
                        case IS_PAUSING: {
                            switchPlayButton(true);
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
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SLOWING_BACK;
                    switchPlayButton(false);
                    slowDeltaTime = TIME_SLOW_VALUE;
//                    mediaPlayerCore.setRate(-0.5f);
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }
            case SUPER_SLOW_BACK: {
                try {
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SUPER_SLOW_BACK;
                    switchPlayButton(false);
                    slowDeltaTime = TIME_SUPPER_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }
            case SLOW_FORWARD: {
                try {
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SLOWING_FORWARD;
                    switchPlayButton(false);
                    slowDeltaTime = TIME_SLOW_VALUE;
                    timeline.playFromStart();
                } catch (Exception ex) {
                }
                break;
            }
            case STEP_BACK: {
                try {
                    System.out.println("STEP_BACK");
                    curPos = this.mediaPlayerCore.getPosition();
                    curPos = curPos - stepDelta;
                    mediaPlayerCore.setPosition(curPos);
                    curTime = mediaPlayerCore.getTime();
                    updateTimeLabel();

                    scrubBar.setValue(curPos);
                    pause();
                } catch (Exception ex) {
                }
                break;
            }
            case STEP_FORWARD: {
                try {
                    System.out.println("STEP_BACK");
                    curPos = this.mediaPlayerCore.getPosition();
                    curPos = curPos + stepDelta;
                    mediaPlayerCore.setPosition(curPos);

                    curTime = mediaPlayerCore.getTime();
                    updateTimeLabel();

                    scrubBar.setValue(curPos);
                    pause();
                } catch (Exception ex) {
                }
                break;
            }

            case SUPER_SLOW_FORWARD: {
                try {
                    mediaPlayerCore.setPause(true);
                    status = VideoStatus.IS_SUPER_SLOW_FORWARD;
                    switchPlayButton(false);
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

    public void setVideoPane(Pane pane) {
        this.videoPane = pane;
        if (videoPane != null) {
            BorderPane borderPane = mediaPlayerCore.getBorderPane();
            videoPane.getChildren().add(0, borderPane);
        }
    }

    public void stopExportImage() {
        timelineExport.stop();
    }

    public void startExportImage(String exportFolder) {
        this.exportFolder = exportFolder;
        timelineExport.playFromStart();
    }

    public void cancelShowingHistory() {
        this.videoHistory.cancelShowHistory();
    }

    public void init() {
        imgList = new HashMap();
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

        if (this.btnPlay != null) {
            this.btnPlay.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                         new EventHandler<MouseEvent>() {
                                             @Override
                                             public void handle(MouseEvent e) {
                                                 consumeEvent(new VideoBoardEvent(VideoButtonType.PLAY_BUTTON));
                                             }
                                         });
        }

        if (this.btnForwardSlow != null) {
            this.btnForwardSlow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                                new EventHandler<MouseEvent>() {
                                                    @Override
                                                    public void handle(MouseEvent e) {
                                                        consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_FORWARD));
                                                    }
                                                });
        }

        if (this.btnRewindSlow != null) {
            this.btnRewindSlow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                               new EventHandler<MouseEvent>() {
                                                   @Override
                                                   public void handle(MouseEvent e) {
                                                       consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_BACK));
                                                   }
                                               });
        }

        if (this.btnRewindSupperSlow != null) {
            this.btnRewindSupperSlow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                                     new EventHandler<MouseEvent>() {
                                                         @Override
                                                         public void handle(MouseEvent e) {
                                                             consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_BACK));
                                                         }
                                                     });
        }

        if (this.btnForwardSupperSlow != null) {
            this.btnForwardSupperSlow.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                                      new EventHandler<MouseEvent>() {
                                                          @Override
                                                          public void handle(MouseEvent e) {
                                                              consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_FORWARD));
                                                          }
                                                      });
        }

        if (this.btnStepb != null) {
            this.btnStepb.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                          new EventHandler<MouseEvent>() {
                                              @Override
                                              public void handle(MouseEvent e) {
                                                  consumeEvent(new VideoBoardEvent(VideoButtonType.STEP_BACK));
                                              }
                                          });
        }

        if (this.btnStepf != null) {
            this.btnStepf.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                          new EventHandler<MouseEvent>() {
                                              @Override
                                              public void handle(MouseEvent e) {
                                                  try {
                                                      consumeEvent(new VideoBoardEvent(VideoButtonType.STEP_FORWARD));
                                                  } catch (Exception ex) {
                                                  }
                                              }
                                          });
        }

        if (this.keyFrameA != null) {
            this.keyFrameA.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.A);
                                                   videoBoardEvent.value = AFrame;
                                                   consumeEvent(videoBoardEvent);
                                               }
                                           });
        }

        if (this.keyFrameT != null) {
            this.keyFrameT.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.T);
                                                   videoBoardEvent.value = TFrame;
                                                   consumeEvent(videoBoardEvent);
                                               }
                                           });
        }

        if (this.keyFrameI != null) {
            this.keyFrameI.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.I);
                                                   videoBoardEvent.value = IFrame;
                                                   consumeEvent(videoBoardEvent);
                                               }
                                           });
        }

        if (this.keyFrameF != null) {
            this.keyFrameF.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                           new EventHandler<MouseEvent>() {
                                               @Override
                                               public void handle(MouseEvent e) {
                                                   VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.F);
                                                   videoBoardEvent.value = FFrame;
                                                   consumeEvent(videoBoardEvent);
                                               }
                                           });
        }

        if (this.scrubBar != null) {
            scrubBar.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    flag = true;
                    System.out.println("scrubbar setOnMouseDragEntered");
                    consumeEvent(new VideoBoardEvent(VideoButtonType.SCRUB_MOUSE_DRAG_ENTERED));
                }
            });

            this.scrubBar.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                    if (flag) {
                        System.out.println("scrubbar change");
                        VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.SCRUB_CHANGE);
                        videoBoardEvent.value = new_val.floatValue();
                        consumeEvent(videoBoardEvent);
                    }
                }
            });

            scrubBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    flag = true;
                    System.out.println("scrubbar setOnMouseDragged");
                    consumeEvent(new VideoBoardEvent(VideoButtonType.SCRUB_MOUSE_DRAGGED));
                }
            });

            scrubBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    flag = true;
                    System.out.println("scrubbar setOnMouseClicked");
                    consumeEvent(new VideoBoardEvent(VideoButtonType.SCRUB_MOUSE_CLICKED));
                }
            });
        }
    }

    public byte getStatus() {
        byte standByStatus = 0;
        switch (status) {
            case IS_ENDSONG: {
                standByStatus = 7;
                break;
            }
            case IS_PAUSING: {
                standByStatus = 0;
                break;
            }
            case IS_PLAYING: {
                standByStatus = 5;
                break;
            }
            case IS_SLOWING_BACK: {
                standByStatus = 1;
                break;
            }
            case IS_SLOWING_FORWARD: {
                standByStatus = 2;
                break;
            }
            case NOT_STARTED: {
                standByStatus = 6;
                break;
            }
            case IS_SUPER_SLOW_FORWARD: {
                standByStatus = 4;
                break;
            }
            case IS_SUPER_SLOW_BACK: {
                standByStatus = 3;
                break;
            }
        }
        return standByStatus;
    }

    public boolean isStarted() {
        return isStarted;
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

    private void playNormally() {
        timeline.stop();
        curRate = 1f;
        mediaPlayerCore.setRate(curRate);
        resume();
    }

    private void switchPlayButton(boolean isPlayingMode) {
        if (isPlayingMode) {
            btnPlay.getStyleClass().remove("play_button");
            btnPlay.getStyleClass().add("stop_button");
        } else {
            btnPlay.getStyleClass().remove("stop_button");
            btnPlay.getStyleClass().add("play_button");
        }
    }

    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            processSlowDown();
        }
    };

    private final EventHandler<ActionEvent> nextFrameMotion = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            try {
                int frame = (int) (mediaPlayerCore.curTime / 33.3333);
                if (frame > 0) {
                    if (iVideoEvent != null) {
                        iVideoEvent.frameChanged(frame);
                    }
                }
            } catch (Exception ex) {
            }
        }
    };

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

    public void cleanHistory() {
        this.videoHistory.cleanHistory();
    }

    public void finish() {
        this.videoHistory.finish();
    }

    public void consumeStandByEvent(VideoData videoData) {

        VideoStatus status = videoData.getStatus();
        if (status == VideoStatus.IS_ENDSONG) {
            dispose();
        }

        // ithe video source is changed when standby then need to dispose the current video and open the new one
        if (this.filePath != videoData.getFilePath()) {
            this.mediaPlayerCore.setFilePath(videoData.getFilePath());
//            VideoBoardEvent videoBoardEvent = new VideoBoardEvent(VideoButtonType.SET_SOURCE_URL);
//            videoBoardEvent.filePath = videoData.getSourceURL();
//            consumeEvent(videoBoardEvent);
        }

        long frame = videoData.getFrame();

        setFrame(frame);

        switch (status) {
            case IS_PAUSING: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.STOP));
                break;
            }
            case IS_SLOWING_BACK: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_BACK));
                break;
            }
            case IS_SLOWING_FORWARD: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_FORWARD));
                break;
            }
            case IS_SUPER_SLOW_BACK: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_BACK));
                break;
            }
            case IS_SUPER_SLOW_FORWARD: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_FORWARD));
                break;
            }
            case IS_PLAYING: {
                if (!isStarted) {
                    startPlay();
                }
                consumeEvent(new VideoBoardEvent(VideoButtonType.PLAY));
                break;
            }
        }
    }

    public void standByHistory() {
//        this.videoHistory.setStatus((byte) 1);
        this.videoHistory.pause();
    }

    public void continueHistory() {
        this.videoHistory.resumeHistory();
//        this.videoHistory.setStatus((byte) 0);
    }

    public void addStandByHistory() {
        VideoData videoData = new VideoData();
        videoData.setFrame(getFrame());
        videoData.setStatus(status);
        videoData.setSourceURL(this.sourceURL);
        videoData.setFilePath(this.filePath);

        videoHistory.addStandby(videoData);
    }

    private final EventHandler<ActionEvent> nextFrameExport = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            Image image = getSnapShot();

            frame++;

            imgList.put(frame, image);
        }
    };

    private void processSlowDown() {
        try {
            curPos = this.mediaPlayerCore.getPosition();
            curTime = this.mediaPlayerCore.getTime();

            switch (status) {
                case IS_SUPER_SLOW_BACK:
                case IS_SLOWING_BACK: {
                    curTime = curTime - slowDeltaTime;
                    break;
                }
                case IS_SUPER_SLOW_FORWARD:
                case IS_SLOWING_FORWARD: {
                    curTime = curTime + slowDeltaTime;
                    break;
                }
            }

            if (curTime <= 0 || curTime >= totalTime) {
                return;
            }

            mediaPlayerCore.setTime(curTime);
            float newPos = mediaPlayerCore.getPosition();

            // this is needed because if we don't restrict the scrub bar update value then the video is frozen
            if (Math.abs(newPos - curPos) > 0.0005) {
                curPos = newPos;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        scrubBar.setValue(mediaPlayerCore.getPosition());
                        updateTimeLabel();
                    }
                });
            }
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
                this.showATIF();
            } catch (Exception ex) {

            }
        } catch (Exception ex) {

        }
    }

    @Override
    public void paused(MediaPlayer mediaPlayer) {
        System.out.println("paused");
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
                switchPlayButton(false);
            }
        });
    }

    @Override
    public void timeChanged(MediaPlayer mediaPlayer, long l) {
        mediaPlayerCore.curTime = l;
        System.out.println("timeChanged: " + l);
    }

    @Override
         public void positionChanged(MediaPlayer mediaPlayer, float v) {
        try {
            System.out.println("positionChanged");
            curTime = this.mediaPlayerCore.getTime();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    curPos = v;
                    updateTimeLabel();
                    scrubBar.setValue(curPos);
                }
            });
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
