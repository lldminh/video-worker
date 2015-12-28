package com.golftec.video.production.videoproduction.util;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class VideoResizer {

    public boolean resizeVideo(String sourceVideoURI, String tempDirectory, String finalDirectory,
                               int toWidth, int toHeight, String videoName) throws Exception {
        int videoHeight = ImageCommon.getVideoHeight(sourceVideoURI);
        int videoWidth = ImageCommon.getVideoWidth(sourceVideoURI);

        if (videoHeight == toHeight && videoWidth == toWidth) {
            FileUtils.copyFile(Paths.get(sourceVideoURI).toFile(), Paths.get(finalDirectory, videoName).toFile());
            return true;
        }

        int fps = ImageCommon.getFps(sourceVideoURI);

        ImageCommon.convertMp4ToPng(sourceVideoURI, fps, tempDirectory, "resize");

        int totalImages = getImagesInDirectory(tempDirectory);

        for (int i = 0; i < totalImages; i++) {
            File f = new File(tempDirectory + File.separator + "resize_" + (i + 1) + ".png");
            BufferedImage img = ImageIO.read(f);
            BufferedImage scaled = ImageCommon.resizeImageWithHint(img, toWidth, toHeight, BufferedImage.TYPE_INT_ARGB);
            ImageIO.write(scaled, "png", new File(tempDirectory + File.separator + i + ".png"));
            img.getGraphics().dispose();
//            f.delete();
        }

        ImageCommon.makeMp4FromPng(tempDirectory, "", "", "", finalDirectory, videoName, fps);

//        File file = new File(tempDirectory);
//        File[] files = file.listFiles();
//        for (File f : files) {
//            if (f.isFile() && f.exists())
//                f.delete();
//        }

        cleanUp(tempDirectory);

        return true;
    }

    private int getImagesInDirectory(String tempDirectory) {
        File f = new File(tempDirectory);
        return f.listFiles().length;
    }

    public void cleanUp(String tempDirectory) throws IOException {
        File tempDir = new File(tempDirectory);
        if (tempDir.exists()) {
            FileUtils.deleteDirectory(tempDir);
        }
    }
}
