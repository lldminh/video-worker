package com.golftec.teaching.videoUtil.util;

import com.golftec.teaching.common.GTConstants;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VideoMaker {

    Map listForLeft = null;
    Map listForRight = null;
    Map list = null;
    private String rootPath = "snapshot/";
    private String videoFolderForLeft = "video/";
    private String annotationFolderForLeft = "annotation/";
    private String motionFolderForLeft = "motion/";
    private String videoFolderForRight = "video/";
    private String annotationFolderForRight = "annotation/";
    private String motionFolderForRight = "motion/";
    private String outputFolderForLeft = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/left/";
    private String outputFolderForRight = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/right/";
    private String outPutFollder = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/combine/";

    public VideoMaker() {
        list = new ConcurrentHashMap();
        listForRight = new ConcurrentHashMap();
        listForLeft = new ConcurrentHashMap();
    }

    public String getFolder() {
        return this.rootPath;
    }

    public void setFolder(String folder) {
        this.rootPath = folder;
    }

    public void makeLeftImage() {
        File folder = new File(outputFolderForLeft);
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(folder);
                folder.mkdirs();
            } catch (Exception ex) {
            }
        }

        long frame = 1;
        for (long i = 0; i < getTotalOfFileForLeft(); i++) {
            try {
                String fileName = frame + ".png";

//                System.out.println("making " + fileName);
                Image image1 = null;
                Image image2 = null;
                Image image3 = null;
                try {
                    File file = new File(videoFolderForLeft + fileName);
                    if (file.exists()) {
                        image1 = new Image(new File(videoFolderForLeft + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                }

                try {
                    File file = new File(annotationFolderForLeft + fileName);
                    if (file.exists()) {
                        image2 = new Image(new File(annotationFolderForLeft + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                }

                try {
                    File file = new File(motionFolderForLeft + fileName);
                    if (file.exists()) {
                        image3 = new Image(new File(motionFolderForLeft + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                }

                Canvas canvas = new Canvas();
//                canvas.setWidth(image1.getWidth());
                canvas.setWidth(640);
//                canvas.setHeight(image1.getHeight());
                canvas.setHeight(480);

                GraphicsContext gc = canvas.getGraphicsContext2D();

                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);

                if (image1 != null) {
                    gc.drawImage(image1, 0, 0);
                }

                if (image2 != null) {
                    gc.drawImage(image2, 0, 0);
                }

                if (image3 != null) {
                    gc.drawImage(image3, 0, 0);
                }

                Image wImage = canvas.snapshot(sp, null);

//                VideoFile videoFile = new VideoFile(fileName, wImage);
                saveLeftImageIntoDisk(wImage, fileName);
//                listForLeft.put(srcFrame, videoFile);

                frame++;
            } catch (Exception s) {
                System.out.println(s);
            }
        }
    }

    public void makeRightImage(){
        File folder = new File(outputFolderForRight);
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(folder);
                folder.mkdirs();
            } catch (Exception ex) {
            }
        }

        long frame = 1;
        for (long i = 0; i < getTotalOfFileForRight(); i++) {
            try {
                String fileName = frame + ".png";

                Image image1 = null;
                Image image2 = null;
                Image image3 = null;

                try {
                    File file = new File(videoFolderForRight + fileName);
                    if (file.exists()) {
                        image1 = new Image(new File(videoFolderForRight + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                try {
                    File file = new File(annotationFolderForRight + fileName);
                    if (file.exists()) {
                        image2 = new Image(new File(annotationFolderForRight + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                }

                try {
                    File file = new File(motionFolderForRight + fileName);
                    if (file.exists()) {
                        image3 = new Image(new File(motionFolderForRight + fileName).toURI().toString());
                    }
                } catch (Exception ex) {
                }

                Canvas canvas = new Canvas();
                canvas.setWidth(image1.getWidth());
                canvas.setHeight(image1.getHeight());

                GraphicsContext gc = canvas.getGraphicsContext2D();

                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);

                if (image1 != null) {
                    gc.drawImage(image1, 0, 0);
                }

                if (image2 != null) {
                    gc.drawImage(image2, 0, 0);
                }

                if (image3 != null) {
                    gc.drawImage(image3, 0, 0);
                }

                Image wImage = canvas.snapshot(sp, null);

//                VideoFile videoFile = new VideoFile(fileName, wImage);
//                listForRight.put(srcFrame, videoFile);

                saveRightImageIntoDisk(wImage, fileName);

                frame++;
            } catch (Exception s) {
                System.out.println(s);
            }
        }
    }

    private void saveLeftImageIntoDisk(Image image, String fileName) {
        try {
            File outputFile = new File(outputFolderForLeft + fileName);

//            System.out.println("saving " + fileName);
            try {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        } catch (Exception ex) {

        }
    }

    private void saveRightImageIntoDisk(Image image, String fileName) {
        try {
            File outputFile = new File(outputFolderForRight + fileName);

//            System.out.println("saving " + fileName);
            try {

                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        } catch (Exception ex) {

        }
    }

    public void saveIntoDisk() {

        File file = new File(outPutFollder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }

        for (long i = 1; i <= list.size(); i++) {
            try {
                VideoFile videoFile = (VideoFile) list.get(i);
                if (videoFile != null) {
                    Image image = videoFile.image;
                    File outputFile = new File(outPutFollder + videoFile.fileName);

//                    System.out.println("saving " + videoFile.fileName);
                    try {

                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
                    } catch (Exception s) {
                        System.out.println(s);
                    }
                }
            } catch (Exception ex) {

            }
        }
    }

    private void processCombine() {

        File folder = new File(outPutFollder);
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(folder);
                folder.mkdirs();
            } catch (Exception ex) {
            }
        }

        long totalOfLeft = 0;
        try {
            totalOfLeft = new File(outputFolderForLeft).list().length;
        } catch (Exception ex) {
        }

        long totalOfRight = 0;
        try {
            totalOfRight = new File(outputFolderForRight).list().length;
        } catch (Exception ex) {
        }

        long n = Math.max(totalOfLeft, totalOfRight);

        for (long i = 1; i <= n; i++) {
            Image img1 = null;
            Image img2 = null;
            String fileName = i + ".png";

            try {
                File file = new File(outputFolderForLeft + fileName);
                if (file.exists()) {
                    img1 = new Image(new File(outputFolderForLeft + fileName).toURI().toString());
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }

            try {
                File file = new File(outputFolderForRight + fileName);
                if (file.exists()) {
                    img2 = new Image(new File(outputFolderForRight + fileName).toURI().toString());
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }


            Image img = combineImageEx(img1, img2);

            File outputFile = new File(outPutFollder + fileName);

//            System.out.println("saving " + fileName);
            try {

                ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }
    }

    private Image combineImageEx(Image leftImg, Image rightImg) {
        Canvas canvas = new Canvas(640, 480);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.drawImage(leftImg, 0, 0);
        gc.drawImage(rightImg, 321, 0);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        WritableImage wImage = canvas.snapshot(sp, null);

        return wImage;
    }

    public void start(String videoFolderForLeft, String annotationFolderForLeft, String motionFolderForLeft,
                      String videoFolderForRight, String annotationFolderForRight, String motionFolderForRight) {
        this.videoFolderForLeft = videoFolderForLeft;
        this.annotationFolderForLeft = annotationFolderForLeft;
        this.motionFolderForLeft = motionFolderForLeft;

        this.videoFolderForRight = videoFolderForRight;
        this.annotationFolderForRight = annotationFolderForRight;
        this.motionFolderForRight = motionFolderForRight;

        makeLeftImage();
        makeRightImage();

        processCombine();
    }

    public long getTotalOfFileForLeft() {

        long n1 = 0;
        try {
            n1 = new File(videoFolderForLeft).list().length;
        } catch (Exception ex) {
        }

        long n2 = 0;
        try {
            n2 = new File(motionFolderForLeft).list().length;
        } catch (Exception ex) {
        }

        long n3 = 0;
        try {
            n3 = new File(annotationFolderForLeft).list().length;
        } catch (Exception ex) {
        }

        return Math.max(n3, Math.max(n1, n2));
    }

    public long getTotalOfFile(String folder) {
        return new File(folder).list().length;
    }

    public long getTotalOfFileForRight() {
        try {
            long n1 = 0;
            try {
                n1 = new File(videoFolderForRight).list().length;
            } catch (Exception ex) {
            }

            long n2 = 0;
            try {
                n2 = new File(motionFolderForRight).list().length;
            } catch (Exception ex) {
            }

            long n3 = 0;
            try {
                n3 = new File(annotationFolderForRight).list().length;
            } catch (Exception ex) {
            }

            return Math.max(n3, Math.max(n1, n2));
        } catch (Exception ex) {
            return 0;
        }
    }

    class VideoFile {

        public String fileName = "";
        public Image image = null;

        public VideoFile(String fileName, Image image) {
            this.fileName = fileName;
            this.image = image;
        }
    }
}
