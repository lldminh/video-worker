package com.golftec.teaching.videoUtil.audio;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Teo on 2015-07-16.
 */
public class AudioPlayerEx {

    Clip clip; // Contents of a sampled audio file
    boolean playing = false; // whether the sound is currently playing

    // Length and position of the sound are measured in milliseconds
    int audioLength = 1000; // Length of the sound.
    int audioPosition = 0; // Current position within the sound

    Timer timer; // Updates slider every 100 milliseconds
    File filename;

    SourceDataLine line = null;
    AudioInputStream audioInputStream = null;
    PlayerThread pt;

    class PlayerThread extends Thread {

        public void run() {
            float length = 0;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(filename);
                length = filename.length();
//System.out.println("File length is " + length + " bytes.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            AudioFormat audioFormat = audioInputStream.getFormat();
            int frameSize = audioFormat.getFrameSize();
            float frameRate = audioFormat.getFrameRate();
            float lengthInSeconds = ((length / frameSize) / frameRate);
            audioLength = (int) lengthInSeconds;
            audioLength = audioLength * 1000;

            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);

            timer.start();

            try {
                line = (SourceDataLine) AudioSystem.getLine(info);
                line.open(audioFormat);
            } catch (LineUnavailableException e) {
                e.printStackTrace();
                System.exit(1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            playing = true;

            line.start();
            int nBytesRead = 0;
            byte[] abData = new byte[128000];
            while (nBytesRead != -1) {
                try {
                    nBytesRead = audioInputStream.read(abData, 0, abData.length);
                    int temp[] = new int[abData.length];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (nBytesRead >= 0) {
                    int nBytesWritten = line.write(abData, 0, nBytesRead);
                }
            }

            line.drain();
            line.close();
        }
    }

    public void stop() {
        timer.stop();
        line.stop();
        line.flush();
        line.close();
        pt.interrupt();
        pt = null;

        playing = false;
    }

    /**
     * Skip to the specified position
     */
    public void skip(int position) { // Called when user drags the slider
        if (position < 0 || position > audioLength) return;
        audioPosition = position;
        clip.setMicrosecondPosition(position * 1000);
    }

    // An internal method that updates the progress bar.
// The Timer object calls it 10 times a second.
// If the sound has finished, it resets to the beginning
    void tick() {
        if (playing == true) {
            audioPosition = (int) (line.getMicrosecondPosition() / 1000);
        }
    }
}

