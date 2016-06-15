package com.golftec.teaching.videoUtil.control;

import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.videoUtil.util.Common;
import com.golftec.teaching.videoUtil.util.ImageCommon;
import com.golftec.teaching.videoUtil.util.VideoBoardEvent;
import com.golftec.teaching.videoUtil.util.VideoButtonType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.media.Track;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;

import java.io.File;
import java.io.IOException;

public class FxMediaPlayerControl extends VBox {

    public Button btnPlay;
    public Pane videoPane;
    public Canvas canvas;
    public GraphicsContext gc;
    public Button btnStepb;
    public Button btnStepf;
    public Label lblTime;
    public Button btnSpeed;
    public BorderPane mediaPlayerControlLayout;
    public Slider scrubBar;
    public Button btnFullScreen;

    public AnchorPane ATIFpane;
    public ImageView keyFrameA;
    public ImageView keyFrameT;
    public ImageView keyFrameI;
    public ImageView keyFrameF;

    private MediaPlayer mp;
    private MediaView mediaView;
    private Media media;
    private String videoPath = "";
    private String videoURL = "";
    private SpeedStatus speedStatus = SpeedStatus.NORMAL;

    private int frame = 1;

    private boolean atEndOfMedia = false;

    public VideoHistory videoHistory = null;

    IVideoEvent iVideoEvent = null;
    private Timeline frameTimeline;
    private int FPS = 30; // for final video

    private Integer AFrame = null;
    private Integer TFrame = null;
    private Integer IFrame = null;
    private Integer FFrame = null;

    public double AShift = 0;
    public double TShift = 0;
    public double IShift = 0;
    public double FShift = 0;

    private int frames = 0;
    private int shiftPx = -20;

    protected boolean isFlip = false;

    private Timeline timeline = null;

    private enum SpeedStatus {
        NORMAL,
        SLOW,
        SUPER_SLOW
    }

    private Duration duration;

    public void setIVideoEvent(IVideoEvent iVideoEvent) {
        this.iVideoEvent = iVideoEvent;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getVideoURL() {
        return this.videoURL;
    }

    public void setFlip(boolean flip) {
        this.isFlip = flip;
    }

    public boolean getFlip() {
        return this.isFlip;
    }

    public void load(String videoPath) {
        timeline.stop();
        this.videoPath = videoPath;
        media = new Media(new File(videoPath).toURI().toString());
        if (mp != null) {
            mp.dispose();
        }

        mp = new MediaPlayer(media);
        mediaView = new MediaView(mp);

        setEventHanlerForMP();

        timeline.playFromStart();
    }

    public void resetATIF() {
        this.AFrame = null;
        this.TFrame = null;
        this.IFrame = null;
        this.FFrame = null;

        this.keyFrameA.setVisible(false);
        this.keyFrameT.setVisible(false);
        this.keyFrameI.setVisible(false);
        this.keyFrameF.setVisible(false);
    }

    public FxMediaPlayerControl(IVideoEvent iVideoEvent) {
        this.canvas = new Canvas(320, 480);
        this.iVideoEvent = iVideoEvent;
        this.gc = canvas.getGraphicsContext2D();

        loadUI();
        init();

        videoPane.getChildren().add(canvas);
    }

    public FxMediaPlayerControl(String videoPath, IVideoEvent iVideoEvent) {
        this.canvas = new Canvas(320, 480);
        this.videoPath = videoPath;
        this.iVideoEvent = iVideoEvent;
        this.gc = canvas.getGraphicsContext2D();

        media = new Media(new File(videoPath).toURI().toString());

        media.getTracks().addListener(new ListChangeListener<Track>() {
            public void onChanged(Change<? extends Track> change) {
                System.out.println("Track> " + change.getList());
            }
        });

        media.getMetadata().addListener(new MapChangeListener<String, Object>() {
            public void onChanged(MapChangeListener.Change<? extends String, ? extends Object> change) {
                System.out.println("Metadata> " + change.getKey() + " -> " + change.getValueAdded());
            }
        });

        mp = new MediaPlayer(media);
        mediaView = new MediaView(mp);

        loadUI();
        init();

        videoPane.getChildren().add(canvas);

        setEventHanlerForMP();
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void showATIF() {
        if (AFrame == null || TFrame == null
            || IFrame == null || FFrame == null) {
            return;
        } else {
            final double barWidth = this.scrubBar.getPrefWidth();
            System.out.println("barWidth: " + barWidth);
            final double pxA = barWidth * this.AFrame / this.frames;
            final double pxT = barWidth * this.TFrame / this.frames;
            final double pxI = barWidth * this.IFrame / this.frames;
            final double pxF = barWidth * this.FFrame / this.frames;

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
    }

    public void setMediaView(MediaPlayer mp) {
        this.mp = mp;
    }

    public MediaPlayer getMediaPlayer() {
        return this.mp;
    }

    public void setMediaView(MediaView mediaView) {
        this.mediaView = mediaView;
    }

    public MediaView getMediaView() {
        return this.mediaView;
    }

    private void loadUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/FxMediaPlayer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void switchPlayButton(boolean isPlayingMode) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (isPlayingMode) {
                    btnPlay.getStyleClass().remove("play_button");
                    btnPlay.getStyleClass().add("stop_button");
                } else {
                    btnPlay.getStyleClass().remove("stop_button");
                    btnPlay.getStyleClass().add("play_button");
                }
            }
        });
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes
                                                                   * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60
                                  - durationMinutes * 60;
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours,
                                     elapsedMinutes, elapsedSeconds, durationHours, durationMinutes,
                                     durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d", elapsedMinutes,
                                     elapsedSeconds, durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes,
                                     elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        }
    }

    protected void updateValues() {
        Platform.runLater(new Runnable() {
            public void run() {
                Duration currentTime = mp.getCurrentTime();
                lblTime.setText(formatTime(currentTime, duration));
                scrubBar.setDisable(duration.isUnknown());
                if (!scrubBar.isDisabled() && duration.greaterThan(Duration.ZERO)
                    && !scrubBar.isValueChanging()) {
                    scrubBar.setValue(currentTime.divide(duration).toMillis());
                }
            }
        });
    }

    public void setFrame(long frame) {
        try {
            double time = frame * this.duration.toMillis() / this.frames;
            mp.seek(new Duration(time));
            mp.pause();
            switchPlayButton(false);
            updateValues();
        } catch (Exception ex) {

        }
    }

    public void consumeEvent(VideoBoardEvent videoBoardEvent) {
        switch (videoBoardEvent.videoButtonType) {
            case SET_PATH: {
                try {
                } catch (Exception ex) {
                }
                break;
            }
            case FLIP: {
                isFlip = !isFlip;
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
                break;
            }
            case SCRUB_MOUSE_DRAG_ENTERED: {
                break;
            }
            case SCRUB_MOUSE_DRAGGED: {
                try {
                } catch (Exception ex) {

                }
                break;
            }
            case SCRUB_CHANGE: {
                try {
                } catch (Exception ex) {

                }
                break;
            }
            case STOP: {
                break;
            }

            case PLAY: {
                break;
            }

            case NOT_STARTED: {
                break;
            }

            case SET_SOURCE_URL: {
                try {
                } catch (Exception ex) {

                }
                break;
            }

            case PLAY_BUTTON: {
                try {
                    System.out.println("Video Status: " + mp.getStatus());
                    switch (mp.getStatus()) {
                        case UNKNOWN:
                        case HALTED: {
                            break;
                        }
                        case READY: {
                            mp.play();
                            switchPlayButton(true);
                            break;
                        }
                        case PLAYING: {
                            if (atEndOfMedia == true) {
                                atEndOfMedia = false;
                                mp.seek(mp.getStartTime());
                                mp.play();
                                switchPlayButton(true);
                            } else {
                                mp.pause();
                                switchPlayButton(false);
                            }

                            break;
                        }
                        case STOPPED: {
                            mp.seek(mp.getStartTime());
                            mp.play();
                            switchPlayButton(true);
                            break;
                        }
                        case PAUSED: {
                            mp.play();
                            switchPlayButton(true);
                            break;
                        }
                    }
                } catch (Exception ex) {
                }
                break;
            }
            case SLOW_FORWARD: {
                try {
                    btnSpeed.getStyleClass().remove("rabbit_button");
                    btnSpeed.getStyleClass().add("tortoise_button");
                    speedStatus = SpeedStatus.SLOW;
                    mp.setRate(0.7);
                } catch (Exception ex) {
                }
                break;
            }
            case SUPER_SLOW_FORWARD: {
                try {
                    mp.setRate(0.4);
                    speedStatus = SpeedStatus.SUPER_SLOW;
                    btnSpeed.getStyleClass().remove("tortoise_button");
                    btnSpeed.getStyleClass().add("double_tortoise_button");
                } catch (Exception ex) {
                }
                break;
            }
            case NORMAL_SPEED: {
                mp.setRate(1);
                btnSpeed.getStyleClass().remove("double_tortoise_button");
                btnSpeed.getStyleClass().add("rabbit_button");
                speedStatus = SpeedStatus.NORMAL;
                break;
            }
            case STEP_BACK: {
                try {
                    Duration currentTime = mp.getCurrentTime();
                    Duration delta = duration.multiply(20 / 100);
                    Duration backTime = currentTime.subtract(delta);
                    mp.seek(backTime);
                    mp.pause();
                    switchPlayButton(false);
                } catch (Exception ex) {
                }
                break;
            }
            case STEP_FORWARD: {
                try {
                    Duration currentTime = mp.getCurrentTime();
                    Duration backTime = currentTime.add(new Duration(3000));
                    mp.seek(backTime);
                    mp.pause();
                    switchPlayButton(false);
                } catch (Exception ex) {
                }
                break;
            }
        }
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

    private void setEventHanlerForMP() {

        if (mp != null) {
            mp.setOnReady(new Runnable() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            try {
                                System.out.println("videoPath: " + videoPath);
                                duration = mp.getMedia().getDuration();
                                FPS = ImageCommon.getFps(videoPath);
                                frames = (int) mp.getTotalDuration().toMillis() * FPS / 1000;
                                System.out.println("FPS: " + FPS);
                                System.out.println("Frames: " + frames);
                                showATIF();
                                updateValues();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }
                    });
                }
            });

            mp.currentTimeProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                    updateValues();
                }
            });

            mp.setOnPlaying(new Runnable() {
                public void run() {
                    System.out.println("setOnPlaying");
                }
            });

            mp.setOnEndOfMedia(new Runnable() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            switchPlayButton(false);
                            atEndOfMedia = true;
                        }
                    });
                }
            });
        }
    }

    private void initSnapshotTimeLine() {
        try {
            int quality = 30; // create frame per 30ms
            double duration = 1000 / quality;
            timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrameSnapshot));
            timeline.playFromStart();
        } catch (Exception ex) {
        }
    }

    private void initTimeLine() {
        try {
            int quality = 30; // create frame per 30ms
            double duration = 1000 / quality;
            frameTimeline = new Timeline();
            frameTimeline.setCycleCount(Timeline.INDEFINITE);
            frameTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));
            frameTimeline.playFromStart();
        } catch (Exception ex) {
        }
    }

    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            try {
                int videoFrame = (int) (mp.getCurrentTime().toMillis() * FPS / 1000);
                if (videoFrame > 0) {
                    frame = videoFrame;
                    if (frame > 0) {
                        if (iVideoEvent != null) {
                            iVideoEvent.frameChanged(frame);
                        }
                    }
                }
            } catch (Exception ex) {
            }
        }
    };

    public WritableImage getSnapshot() {
        if (mediaView != null && mp != null) {
            WritableImage wi = new WritableImage(320, 480);
            mediaView.snapshot(new SnapshotParameters(), wi);
            return wi;
        }
        return null;
    }

    private final EventHandler<ActionEvent> nextFrameSnapshot = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            try {
                WritableImage wi = new WritableImage(320, 480);
                mediaView.snapshot(new SnapshotParameters(), wi);

                if (isFlip) {
                    gc.drawImage(Common.invertImage(wi), 0, 0);
                } else {
                    gc.drawImage(wi, 0, 0);
                }
            } catch (Exception ex) {
            }
        }
    };

    public int getCurrentFrame() {
        return this.frame;
    }

    public void init() {
        initTimeLine();
        initSnapshotTimeLine();
        scrubBar.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (scrubBar.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    mp.seek(duration.multiply(scrubBar.getValue()));
                    mp.pause();
                    switchPlayButton(false);
                }
            }
        });

        if (this.btnPlay != null) {
            this.btnPlay.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                         new EventHandler<MouseEvent>() {
                                             @Override
                                             public void handle(MouseEvent e) {
                                                 consumeEvent(new VideoBoardEvent(VideoButtonType.PLAY_BUTTON));
                                             }
                                         });
        }

        if (this.btnFullScreen != null) {
            this.btnFullScreen.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                         new EventHandler<MouseEvent>() {
                                             @Override
                                             public void handle(MouseEvent e) {
                                                 Stage stage = new Stage();
                                                 stage.initModality(Modality.APPLICATION_MODAL);

                                                 final DoubleProperty width = mediaView.fitWidthProperty();
                                                 final DoubleProperty height = mediaView.fitHeightProperty();

                                                 width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
                                                 height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));


                                                 mediaView.setPreserveRatio(true);
                                                 StackPane root = new StackPane();
                                                 root.getChildren().add(mediaView);

                                                 final Scene scene = new Scene(root, 960, 540);
                                                 scene.setFill(Color.BLACK);

                                                 stage.setScene(scene);
                                                 stage.setTitle("Full Screen Video Player");
                                                 stage.setFullScreen(true);
                                                 stage.show();
                                             }
                                         });
        }

        if (this.btnSpeed != null) {
            this.btnSpeed.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                          new EventHandler<MouseEvent>() {
                                              @Override
                                              public void handle(MouseEvent e) {
                                                  switch (speedStatus) {
                                                      case NORMAL: {
                                                          consumeEvent(new VideoBoardEvent(VideoButtonType.SLOW_FORWARD));
                                                          break;
                                                      }
                                                      case SLOW: {
                                                          consumeEvent(new VideoBoardEvent(VideoButtonType.SUPER_SLOW_FORWARD));
                                                          break;
                                                      }
                                                      case SUPER_SLOW: {
                                                          consumeEvent(new VideoBoardEvent(VideoButtonType.NORMAL_SPEED));
                                                          break;
                                                      }
                                                  }
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
                    System.out.println("scrubbar setOnMouseDragEntered");
                }
            });

            this.scrubBar.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> ov,
                                    Number old_val, Number new_val) {
                }
            });

            scrubBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
//                    System.out.println("scrubbar setOnMouseDragged");
                }
            });

            scrubBar.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("scrubbar setOnMouseClicked");
                }
            });
        }
    }

    public void dispose() {
        frameTimeline.stop();
        timeline.stop();
        if (mp != null) {
            mp.dispose();
        }
    }
}

