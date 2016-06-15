package com.golftec.teaching.videoUtil.audio;

import sun.audio.AudioStream;

import java.io.FileInputStream;
import java.io.InputStream;

public class AudioPlayer {
    AudioStream audioStream = null;
    private String filePath = "";

    public AudioPlayer(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void start() {
        try {
            // open the sound file as a Java input stream
            String gongFile = this.filePath;
            InputStream in = new FileInputStream(gongFile);
            // create an audiostream from the inputstream
            audioStream = new AudioStream(in);
//            audioStream.getLength()

            // play the audio clip with the audioplayer class
            sun.audio.AudioPlayer.player.start(audioStream);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void stop() {
        try {
            if (audioStream != null) {
                sun.audio.AudioPlayer.player.stop(audioStream);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
