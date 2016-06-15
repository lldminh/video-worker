package com.golftec.teaching.videoUtil.control;


/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2009, 2010, 2011, 2012, 2013, 2014 Caprica Software Limited.
 */

import com.golftec.teaching.videoUtil.util.Common;
import com.sun.jna.Memory;
import com.sun.jna.NativeLibrary;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.direct.*;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * Example showing how to render video to a JavaFX Canvas component.
 * <p>
 * The target is to render full HD video (1920x1080) at a reasonable srcFrame rate (>25fps).
 * <p>
 * This test can render the video at a fixed size, or it can take the size from the
 * video itself.
 * <p>
 * You may need to set -Djna.library.path=[path-to-libvlc] on the command-line.
 * <p>
 * Based on an example contributed by John Hendrikx.
 * <p>
 * -Dprism.verbose=true -Xmx512m -verbose:gc
 * <p>
 * This version works with JavaFX on JDK 1.8, without "wrong thread" errors.
 */
public class MediaPlayerCore {

    /**
     * Set this to <code>true</code> to resize the display to the dimensions of the
     * video, otherwise it will use {@link #WIDTH} and {@link #HEIGHT}.
     */
    private static final boolean useSourceSize = true;
    /**
     * Target width, unless {@link #useSourceSize} is set.
     */
    private static final int WIDTH = 1280;
    /**
     * Target height, unless {@link #useSourceSize} is set.
     */
    private static final int HEIGHT = 720;
    private static final Logger log = LoggerFactory.getLogger(MediaPlayerCore.class);

    static {
//        System.out.println(System.getProperty("user.dir") );
//        log.info(System.getProperty("user.dir"));
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), System.getProperty("user.dir") + "/vlc/");

        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/VLC-2.2.1");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Program Files/VideoLAN/VLC/");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:/Work/MonsterPixel/GolfTec/source/local/golftec-teaching-local/Library/vlc/");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Data\\GolfTEC\\Development\\Source\\Library\\vlc");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/Applications/VLC.app/Contents/MacOS/lib/");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/Users/hoang/vlc1/Contents/MacOS/lib/");
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/Users/hoang/Projects/Lib/vlc/lib");
//
//        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "/home/hoang/golftec-teaching-server/misc/VLC/MacOS/lib");
    }

    /**
     * Lightweight JavaFX canvas, the video is rendered here.
     */
    private final Canvas canvas;
    /**
     * Pixel writer to update the canvas.
     */
    private final PixelWriter pixelWriter;
    /**
     * Pixel format.
     */
    private final WritablePixelFormat<ByteBuffer> pixelFormat;
    /**
     *
     */
    private final BorderPane borderPane;
    /**
     * The vlcj direct rendering media player component.
     */
    private final DirectMediaPlayerComponent mediaPlayerComponent;
    /**
     *
     */
    private final Timeline timeline;
    public long curTime = 0;
    public int FPS = 30;
    /**
     * Filename of the video to play.
     */
//    private static final String VIDEO_FILE = "C:\\Wildlife.wmv";
    private String filePath = "";
    private boolean isHorizonalFlip = false;
    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
                curTime += 1000 / FPS;
            }

            Memory[] nativeBuffers = mediaPlayerComponent.getMediaPlayer().lock();
            if (nativeBuffers != null) {
                // FIXME there may be more efficient ways to do this...
                // Since this is now being called by a specific rendering time, independent of the native video callbacks being
                // invoked, some more defensive conditional checks are needed
//                System.out.println("length: " + nativeBuffers.length);
                Memory nativeBuffer = nativeBuffers[0];

                if (nativeBuffer != null) {
                    ByteBuffer byteBuffer = nativeBuffer.getByteBuffer(0, nativeBuffer.size());
                    BufferFormat bufferFormat = ((DefaultDirectMediaPlayer) mediaPlayerComponent.getMediaPlayer()).getBufferFormat();
                    if (bufferFormat.getWidth() > 0 && bufferFormat.getHeight() > 0) {
//                        System.out.println("HAHA: " + curTime);
                        if (isHorizonalFlip) {
                            Canvas canvas1 = new Canvas(canvas.getWidth(), canvas.getHeight());
                            PixelWriter pixelWriter1 = canvas1.getGraphicsContext2D().getPixelWriter();

                            pixelWriter1.setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer, bufferFormat.getPitches()[0]);
                            Image image = canvas1.snapshot(null, null);

                            canvas.getGraphicsContext2D().drawImage(Common.invertImage(image), 0, 0, image.getWidth(), image.getHeight());
                        } else {
                            pixelWriter.setPixels(0, 0, bufferFormat.getWidth(), bufferFormat.getHeight(), pixelFormat, byteBuffer, bufferFormat.getPitches()[0]);
                        }
                    }
                }
            }

            mediaPlayerComponent.getMediaPlayer().unlock();
        }
    };

    /**
     *
     */
    public MediaPlayerCore(String filePath, MediaPlayerEventListener mediaPlayerEventListener) {

        this.filePath = filePath;
        canvas = new Canvas();

        pixelWriter = canvas.getGraphicsContext2D().getPixelWriter();
        pixelFormat = PixelFormat.getByteBgraInstance();

        borderPane = new BorderPane();
        borderPane.setCenter(canvas);

        mediaPlayerComponent = new TestMediaPlayerComponent();
        mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(mediaPlayerEventListener);

        // FIXME change to suit

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        double duration = 1000 / FPS;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));
    }

    public void setRate(float value) {
        mediaPlayerComponent.getMediaPlayer().setRate(value);
    }

    public void pause() {
        mediaPlayerComponent.getMediaPlayer().pause();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void play() throws Exception {
        System.out.println("File: " + this.filePath);

        File file = new File(this.filePath);
        if (!file.exists()) {
            System.out.println("File: " + this.filePath + " not exist");
        } else {
            System.out.println("File can-read: " + file.canRead() + ", can-write: " + file.canWrite() + ", can-exe: " + file.canExecute());
            try {
                mediaPlayerComponent.getMediaPlayer().playMedia(this.filePath);
                timeline.playFromStart();
            } catch (Exception ex) {
                log.error("", ex);
            }
        }
    }

    public boolean getHorizonalFlip() {
        return this.isHorizonalFlip;
    }

    public void setHorizonalFlip(boolean horizonalFlip) {
        this.isHorizonalFlip = horizonalFlip;
    }

    public void stop() throws Exception {
        mediaPlayerComponent.getMediaPlayer().stop();
    }

    public int getTotalTime() throws Exception {
        int durationInSeconds = (int) (mediaPlayerComponent.getMediaPlayer().getMediaMeta().getLength() / 1000);
        return durationInSeconds;
    }

    public int getTotalTimeByMillisecond() throws Exception {
        int duration = (int) mediaPlayerComponent.getMediaPlayer().getMediaMeta().getLength();
        return duration;
    }

    public float getPosition() {
        return mediaPlayerComponent.getMediaPlayer().getPosition();
    }

    public void setPosition(float value) {
        mediaPlayerComponent.getMediaPlayer().setPosition(value);
        curTime = mediaPlayerComponent.getMediaPlayer().getTime();
    }

    public long getTime() throws Exception {
        return mediaPlayerComponent.getMediaPlayer().getTime();
    }

    public void setTime(long time) throws Exception {
        this.curTime = time;
        mediaPlayerComponent.getMediaPlayer().setTime(time);
    }

    public boolean isPlaying() {
        return mediaPlayerComponent.getMediaPlayer().isPlaying();
    }

    public void setPause(boolean pause) throws Exception {
        mediaPlayerComponent.getMediaPlayer().setPause(pause);
    }

    public MediaPlayer getMediaPlayer(){
        return mediaPlayerComponent.getMediaPlayer();
    }

    public boolean canPause() {
        try {
            return mediaPlayerComponent.getMediaPlayer().canPause();
        } catch (Exception ex) {

        }
        return false;
    }

    public boolean isPlayable() {
        return mediaPlayerComponent.getMediaPlayer().isPlayable();
    }

    public void dispose() throws Exception {
        try {
            timeline.stop();

            mediaPlayerComponent.getMediaPlayer().stop();
            mediaPlayerComponent.getMediaPlayer().release();
        } catch (Exception ex) {

        }

//        if (mediaPlayerComponent.getMediaPlayer().isPlayable()) {
//            timeline.stop();
//
//            mediaPlayerComponent.getMediaPlayer().stop();
//            mediaPlayerComponent.getMediaPlayer().release();
//            System.out.println("media player is disposed");
//        }
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    /**
     * Implementation of a direct rendering media player component that renders
     * the video to a JavaFX canvas.
     */
    private class TestMediaPlayerComponent extends DirectMediaPlayerComponent {

        public TestMediaPlayerComponent() {
            super(new TestBufferFormatCallback());
        }

        @Override
        protected RenderCallback onGetRenderCallback() {
            return new TestRenderCallbackAdapter();
        }
    }

    /**
     * Callback to get the buffer format to use for video playback.
     */
    private class TestBufferFormatCallback implements BufferFormatCallback {

        @Override
        public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
            System.out.println("TestBufferFormatCallback: ");
            if (sourceWidth > 320) {
                sourceWidth = 320;
            }

            if (sourceHeight > 480) {
                sourceHeight = 480;
            }

//            sourceWidth = sourceWidth * 2;
//            sourceHeight = sourceHeight * 2;

            final int width;
            final int height;
            if (useSourceSize) {
                width = sourceWidth;
                height = sourceHeight;
            } else {
                width = WIDTH;
                height = HEIGHT;
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    canvas.setWidth(width);
                    canvas.setHeight(height);
                }
            });
            return new RV32BufferFormat(width, height);
        }
    }

    private class TestRenderCallbackAdapter extends RenderCallbackAdapter {

        private TestRenderCallbackAdapter() {
            super(new int[200 * 3000]);
        }

        @Override
        protected void onDisplay(DirectMediaPlayer directMediaPlayer, int[] ints) {
        }
    }
}
