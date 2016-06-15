package com.golftec.teaching.videoUtil.util;

import com.coremedia.iso.boxes.TimeToSampleBox;
import com.google.common.base.Strings;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageCommon {

    public static BufferedImage addImages(BufferedImage firstImage, BufferedImage secondImage,
                                          int newImageWidth, int newImageHeight,
                                          int positionX, int positionY) {
        BufferedImage finalImage = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);

        Graphics2D g = finalImage.createGraphics();
        g.drawImage(firstImage, 0, 0, null);
        g.drawImage(secondImage, positionX, positionY, null);
        g.dispose();
        return finalImage;
    }

    /**
     * Takes two buffered images and returns a new buffered image that
     * combines the leftImage and the rightImage into one.
     *
     * @param leftImage
     * @param rightImage
     * @return
     */
    public static BufferedImage joinImagesLeftToRight(BufferedImage leftImage, BufferedImage rightImage,
                                                      int centerPixelsToInsert) {
        BufferedImage finalImage = new BufferedImage((leftImage.getWidth() + rightImage.getWidth()), leftImage.getHeight(),
                                                     BufferedImage.TYPE_INT_RGB);
        Graphics2D g = finalImage.createGraphics();
        g.drawImage(leftImage, 0, 0, null);
        if (centerPixelsToInsert > 0) {
            g.drawImage(getCenterPixels(leftImage.getWidth(), centerPixelsToInsert), leftImage.getWidth(), 0, null);
        }
        g.drawImage(rightImage, leftImage.getWidth() + 4, 0, null);
        g.dispose();
        return finalImage;
    }

    public static BufferedImage cropImageWidth(BufferedImage source, int offset, int newImageWidth) {
        int extraPixels = source.getWidth() - newImageWidth;
        int startPixel = 0;
        if (offset < 0)
            startPixel = (extraPixels / 2) + offset - 4;
        else
            startPixel = offset + (extraPixels / 2);
        return source.getSubimage(startPixel, 0, newImageWidth, source.getHeight());
    }

    public static BufferedImage invertImage(BufferedImage source) {
        BufferedImage buff = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D graphics = buff.createGraphics();
        graphics.drawImage(source, 0, 0, source.getWidth(), source.getHeight(),
                           source.getWidth(), 0, 0, source.getHeight(), null);
        graphics.dispose();
        return buff;
    }

    /**
     * Converts an entire MP4 to individual frames as %PATH%/%VIDEONAME%_decimal.png
     *
     * @param videoUri
     * @param frameRate
     * @param path
     * @param pngName
     * @return the output the system gathered.
     * @throws java.io.IOException
     */
    public static String convertMp4ToPng(String videoUri, int frameRate, String path, String pngName) throws IOException {
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(videoUri);
        command.add("-r");
        command.add("" + frameRate);
        command.add("-f");
        command.add("image2");
        command.add(path + File.separator + pngName + "_%d.png");

        ProcessBuilder builder = new ProcessBuilder(command);
        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        String composite = "";
        while ((line = br.readLine()) != null) {
            composite += line;
        }
        return composite;
    }

    public static String makeThumbnail(String videoUri, String outPut) throws IOException {
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-i");
        command.add(videoUri);
        command.add("-ss");
        command.add("00:00:00.000");
        command.add("-vframes");
        command.add("1");
        command.add(outPut);

        ProcessBuilder builder = new ProcessBuilder(command);
        final Process process = builder.start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line = "";
        String composite = "";
        while ((line = br.readLine()) != null) {
            composite += line;
        }
        return composite;
    }

    public static String makeMp4FromPng(String pathToPng, String pngFileName,
                                        String pathToWave, String waveFileName,
                                        String videoSavePath, String videoName,
                                        int fps) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add("ffmpeg");
        command.add("-r");
        command.add("" + fps);
        command.add("-i");
        command.add(pathToPng + File.separator + pngFileName + "%d.png");
        if (!Strings.isNullOrEmpty(waveFileName)) {
            command.add("-i");
            command.add(pathToWave + File.separator + waveFileName + ".wav");
        }
        command.add("-r");
        command.add("" + fps);
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add(videoSavePath + File.separator + videoName);

        ProcessBuilder builder = new ProcessBuilder(command);
        final Process process = builder.start();

        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
        String composite = "";
        String errorString = "";
        /**BufferedReader br = new BufferedReader(isr);
         String line = "";

         while ((line = br.readLine()) != null) {
         System.out.println(line);
         composite += line;
         }**/
        StreamBoozer sbin = new StreamBoozer(is);
        StreamBoozer sberr = new StreamBoozer(process.getErrorStream());
        sbin.start();
        sberr.start();
        process.waitFor();
        sbin.join();
        sberr.join();
        composite = sbin.output;
        errorString = sberr.output;
        return composite;
    }

    public static int getFps(String videoUri) throws FileNotFoundException, IOException {
        RandomAccessFile accessFile = new RandomAccessFile(videoUri, "rw");
        Movie video = MovieCreator.build(accessFile.getChannel());
        //gets the total time of a segment of the video.
        //once we have that, we can divide by the tracks in that segment and come 
        //up with a fps.
        long[] sampleTimes = TimeToSampleBox.blowupTimeToSamples(video.getTracks().get(0).getDecodingTimeEntries());
        long duration = 0;
        for (long sampleTime : sampleTimes) {
            duration += sampleTime;
        }

        long durPerSample = duration / video.getTracks().get(0).getSamples().size();
        float fps = (float) video.getTracks().get(0).getTrackMetaData().getTimescale() / durPerSample;
        accessFile.close();
        return new Float(fps).intValue();
    }

    public static int getVideoWidth(String videoUri) throws Exception {
        File f = new File(videoUri);
        if (!f.exists()) {
            throw new IOException("Video File Does Not Exist!");
        }
        List<String> command = new ArrayList<String>();
        command.add("ffprobe");
        command.add("-i");
        command.add(videoUri);
        command.add("-show_entries");
        command.add("stream=width");

        ProcessBuilder builder = new ProcessBuilder(command);
        final Process process = builder.start();
        InputStream is = process.getInputStream();
//	    InputStreamReader isr = new InputStreamReader(is);
//	    BufferedReader br = new BufferedReader(isr);
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

        if (Strings.isNullOrEmpty(composite)) {
            System.out.println("can't get width: " + videoUri);
            return 0;
        }
        if (composite.indexOf("width=") == -1) {
            return 0;
        }

        composite = composite.toLowerCase();
        composite = StringUtils.substringBetween(composite, "[stream]", "[/stream]");
        composite = StringUtils.trim(composite);
        composite = StringUtils.removeStartIgnoreCase(composite, "width=");

        return Integer.parseInt(composite.trim());
    }

    public static long getLength(String videoUri) throws Exception {
        try {
            File f = new File(videoUri);
            if (!f.exists()) {
                throw new IOException("File Does Not Exist!");
            }
            List<String> command = new ArrayList<String>();
            command.add("ffprobe");
            command.add("-i");
            command.add(videoUri);
            command.add("-show_entries");
            command.add("stream=duration");

            ProcessBuilder builder = new ProcessBuilder(command);
            final Process process = builder.start();
            InputStream is = process.getInputStream();
//	    InputStreamReader isr = new InputStreamReader(is);
//	    BufferedReader br = new BufferedReader(isr);
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

            if (Strings.isNullOrEmpty(composite)) {
                System.out.println("can't get length: " + videoUri);
                return 0;
            }
            if (composite.indexOf("duration=") == -1) {
                return 0;
            }

            composite = composite.toLowerCase();
            composite = StringUtils.substringBetween(composite, "[stream]", "[/stream]");
            composite = StringUtils.trim(composite);
            composite = StringUtils.removeStartIgnoreCase(composite, "duration=");

            double length = Double.parseDouble(composite.trim());
            return (long)(length * 1000);
        }catch (Exception ex){
            System.out.println("can't get length: " + videoUri);
        }
        return 0;
    }

    public static int getVideoHeight(String videoUri) throws Exception {
        File f = new File(videoUri);
        if (!f.exists()) {
            throw new IOException("Video File Does Not Exist!");
        }
        List<String> command = new ArrayList<String>();
        command.add("ffprobe");
        command.add("-i");
        command.add(videoUri);
        command.add("-show_entries");
        command.add("stream=height");

        ProcessBuilder builder = new ProcessBuilder(command);
        final Process process = builder.start();
        InputStream is = process.getInputStream();
//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader br = new BufferedReader(isr);
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

        if (Strings.isNullOrEmpty(composite)) {
            System.out.println("can't get height: " + videoUri);
            return 0;
        }
        if (composite.indexOf("height=") == -1) {
            return 0;
        }

        composite = composite.toLowerCase();
        composite = StringUtils.substringBetween(composite, "[stream]", "[/stream]");
        composite = StringUtils.trim(composite);
        composite = StringUtils.removeStartIgnoreCase(composite, "height=");

        return Integer.parseInt(composite.trim());
    }

    public static BufferedImage resizeImageWithHint(BufferedImage originalImage, int newWidth, int newHeight,
                                                    int type) {

        /**BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
         Graphics2D g = resizedImage.createGraphics();
         g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
         //g.dispose();

         return resizedImage;
         **/
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                           RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                           RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }

    private static BufferedImage getCenterPixels(int startPosition, int totalPixelsToAdd) {
        BufferedImage centerPixels = new BufferedImage(totalPixelsToAdd, startPosition, BufferedImage.TYPE_INT_RGB);
        return centerPixels;
    }
}
