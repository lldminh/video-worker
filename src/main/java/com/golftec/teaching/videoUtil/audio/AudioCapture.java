package com.golftec.teaching.videoUtil.audio;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Date;
import java.util.function.Consumer;

public class AudioCapture {

    AudioFormat audioFormat;
    TargetDataLine targetDataLine;

    private static final Log log = LogFactory.getLog(AudioCapture.class);

    String savePath = "";
    String fileName = "";
    ByteArrayOutputStream out = null;
    boolean running = true;
    boolean paused = false;
    private String tmpPath = "";

    Consumer<String> callback;


    public AudioCapture() {
    }


    public void setTmpPath(String path) {
        this.tmpPath = path;
    }

    public void start() {
        captureAudio();
    }

    public void pause() {
        paused = true;
//        targetDataLine.stop();
    }

    public void resume() {
        paused = false;
//        targetDataLine;
    }

    public void finish(Consumer<String> callback) {
        this.callback = callback;
        paused = false;
        running = false;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    //This method creates and returns an
    // AudioFormat object for a given set of format
    // parameters.  If these parameters don't work
    // well for you, try some of the other
    // allowable parameter values, which are shown
    // in comments following the declarations.
    private AudioFormat getAudioFormat() {
        float sampleRate = 8000.0F;
        //8000,11025,16000,22050,44100
        int sampleSizeInBits = 16;
        //8,16
        int channels = 1;
        //1,2
        boolean signed = true;
        //true,false
        boolean bigEndian = false;
        //true,false
        return new AudioFormat(sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }//end getAudioFormat

    //This method captures audio input from a
    // microphone and saves it in an audio file.
    private void captureAudio() {
        try {
            //Get things set up for capture
            audioFormat = getAudioFormat();
            DataLine.Info dataLineInfo =
                    new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat);
            targetDataLine = (TargetDataLine)
                    AudioSystem.getLine(dataLineInfo);

            //Create a thread to capture the microphone
            // data into an audio file and start the
            // thread running.  It will run until the
            // Stop button is clicked.  This method
            // will return after starting the thread.
            new CaptureThread().start();
        } catch (Exception e) {
            log.error(e);
        }//end catch
    }//end captureAudio method

    private void saveFile() {
        log.info("Saving file ...");
        try {
            AudioFileFormat.Type fileType = null;
            File audioFile = null;

            //Set the file type and the file extension
//                fileType = AudioFileFormat.Type.AIFC;
//                fileType = AudioFileFormat.Type.AIFF;
//                fileType = AudioFileFormat.Type.AU;
//                fileType = AudioFileFormat.Type.SND;
//                fileType = AudioFileFormat.Type.WAVE;

            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            } else {
                file.delete();
                file.mkdirs();
            }

            fileName = Long.toString(new Date().getTime()) + ".wav";
//            fileName = "audio.wav";

            fileType = AudioFileFormat.Type.WAVE;
            audioFile = new File(tmpPath + fileName);

            try {
                byte audio[] = out.toByteArray();
                InputStream input = new ByteArrayInputStream(audio);
                final AudioFormat format = getAudioFormat();
                final AudioInputStream ais = new AudioInputStream(input, format, audio.length / format.getFrameSize());
                AudioSystem.write(ais, fileType, audioFile);
            } catch (Exception e) {
                log.error(e);
            }
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    //Inner class to capture data from microphone
// and write it to an output audio file.
    class CaptureThread extends Thread {

        public void run() {

            try {
                targetDataLine.open(audioFormat);
                targetDataLine.start();

                out = new ByteArrayOutputStream();
                running = true;
                int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
                byte buffer[] = new byte[bufferSize];
                try {
                    while (running) {
                        if (!paused) {
                            int count = targetDataLine.read(buffer, 0, buffer.length);
                            if (count > 0) {
                                out.write(buffer, 0, count);
                                InputStream input = new ByteArrayInputStream(buffer);
                                final AudioInputStream ais = new AudioInputStream(input, audioFormat, buffer.length / audioFormat.getFrameSize());
                            }
                        } else {
                            sleep(10);
                        }
                    }

                    out.close();
                    saveFile();

                    // copy and close
                    File src = new File(tmpPath + fileName);

                    File dst = new File(savePath + File.separator + "audio.wav");

                    try {
                        log.info("src.toPath(): " + src.toPath());
                        log.info("dst.toPath(): " + dst.toPath());

                        FileUtils.copyFile(src, dst);
                    } catch (Exception ex) {
                        log.info("cannot copy audio file: " + ex.getMessage());
                    }

                    targetDataLine.stop();
                    targetDataLine.close();

                    if (callback != null) {
                        callback.accept("OK");
                    }
                } catch (IOException e) {
                    log.error(e);
                }
//                AudioSystem.write(
//                        new AudioInputStream(targetDataLine),
//                        fileType,
//                        audioFile);
            } catch (Exception e) {
                log.error(e);
//                e.printStackTrace();
            }//end catch
        }//end run
    }//end inner class CaptureThread
//=============================================//
}
