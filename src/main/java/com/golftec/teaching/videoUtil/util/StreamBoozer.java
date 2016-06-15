package com.golftec.teaching.videoUtil.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by hoang on 2015-07-01.
 */
public class StreamBoozer extends Thread {

    public String output;
    private InputStream in;

    public StreamBoozer(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try (Scanner br = new Scanner(new InputStreamReader(in))) {
            while (br.hasNextLine()) {
                output += br.nextLine() + System.getProperty("line.separator");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
