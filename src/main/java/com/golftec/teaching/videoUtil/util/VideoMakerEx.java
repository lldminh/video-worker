package com.golftec.teaching.videoUtil.util;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class VideoMakerEx {

    Map listForLeft = null;
    Map listForRight = null;
    Map list = null;
    private String videoFolderForLeft = "video/";
    private String annotationFolderForLeft = "annotation/";
    private String motionFolderForLeft = "motion/";
    private String videoFolderForRight = "video/";
    private String annotationFolderForRight = "annotation/";
    private String motionFolderForRight = "motion/";
    private String outputFolderForLeft = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/left/";
    private String outputFolderForRight = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/right/";
    private String outPutCombineFollder = GTConstants.BASE_VIDEO_FACTORY_DIR + "output/combine/";
    private int numberOfThreads = 7;

    public VideoMakerEx() {
        list = new ConcurrentHashMap();
        listForRight = new ConcurrentHashMap();
        listForLeft = new ConcurrentHashMap();
    }

    public void setNumberOfThreads(int numberOfThreads){
        this.numberOfThreads = numberOfThreads;
    }

    public void setOutputFolderForLeft(String folder) {
        this.outputFolderForLeft = folder;
    }

    public void setOutputFolderForRight(String folder) {
        this.outputFolderForRight = folder;
    }

    public void setOutPutCombineFollder(String folder) {
        this.outPutCombineFollder = folder;
    }

    private void makeLeftImageFrame(int frame) {
        String fileName = frame + ".png";

//        System.out.println("making " + fileName);
        BufferedImage image1 = null;
        BufferedImage image2 = null;
        BufferedImage image3 = null;
        try {
            File file = new File(videoFolderForLeft + fileName);
            if (file.exists()) {
                try {
                    image1 = ImageIO.read(new File(videoFolderForLeft + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
        }

        try {
            File file = new File(annotationFolderForLeft + fileName);
            if (file.exists()) {
                try {
                    image2 = ImageIO.read(new File(annotationFolderForLeft + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
        }

        try {
            File file = new File(motionFolderForLeft + fileName);
            if (file.exists()) {
                try {
                    image3 = ImageIO.read(new File(motionFolderForLeft + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
        }

        BufferedImage bi = new BufferedImage(320, 480, BufferedImage.TYPE_INT_ARGB);

        Graphics gc = bi.createGraphics();

        if (image1 != null) {
            gc.drawImage(image1, 0, 0, null);
        }

        if (image2 != null) {
            gc.drawImage(image2, 0, 0, null);
        }

        if (image3 != null) {
            gc.drawImage(image3, 0, 0, null);
        }

        File outputFile = new File(outputFolderForLeft + fileName);

//        System.out.println("saving " + fileName);
        try {
            ImageIO.write(bi, "PNG", outputFile);
        } catch (Exception s) {
            System.out.println(s);
        }
    }

    public void makeLeftImage() {
        try {
            GTUtil.deleteFileSafely(Paths.get(outputFolderForLeft));
            Files.createDirectories(Paths.get(outputFolderForLeft));

            Integer frame = 1;

            BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<Integer>((int) getTotalOfFileForLeft());
            for (Integer i = 0; i < getTotalOfFileForLeft(); i++) {
                blockingDeque.add(frame);
                frame++;
            }

            java.util.List<Thread> threads = new ArrayList<>();

            for (int i = 0; i < numberOfThreads; i++) {
                Thread thread = new Thread() {
                    public void run() {
                        Integer frame = 0;
                        while ((frame = blockingDeque.poll()) != null) {
                            makeLeftImageFrame(frame);
                        }
                    }
                };
                threads.add(thread);
                thread.start();
            }

            for (int i = 0; i < threads.size(); i++) {
                Thread thread = threads.get(i);
                thread.join();
            }
        } catch (Exception ex) {
        }

//        for (long i = 0; i < getTotalOfFileForLeft(); i++) {
//            try {
//                makeLeftImageFrame(frame);
//                frame++;
//            } catch (Exception s) {
//                System.out.println(s);
//            }
//        }
    }

    private void makeRightImageFrame(int frame) {
        String fileName = frame + ".png";

        BufferedImage image1 = null;
        BufferedImage image2 = null;
        BufferedImage image3 = null;

        try {
            File file = new File(videoFolderForRight + fileName);
            if (file.exists()) {
                try {
                    image1 = ImageIO.read(new File(videoFolderForRight + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            File file = new File(annotationFolderForRight + fileName);
            if (file.exists()) {
                try {
                    image2 = ImageIO.read(new File(annotationFolderForRight + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
        }

        try {
            File file = new File(motionFolderForRight + fileName);
            if (file.exists()) {
                try {
                    image3 = ImageIO.read(new File(motionFolderForRight + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
        }

        BufferedImage bi = new BufferedImage(320, 480, BufferedImage.TYPE_INT_ARGB);

        Graphics gc = bi.createGraphics();

        if (image1 != null) {
            gc.drawImage(image1, 0, 0, null);
        }

        if (image2 != null) {
            gc.drawImage(image2, 0, 0, null);
        }

        if (image3 != null) {
            gc.drawImage(image3, 0, 0, null);
        }

        File outputFile = new File(outputFolderForRight + fileName);

//        System.out.println("saving " + fileName);
        try {
            ImageIO.write(bi, "PNG", outputFile);
        } catch (Exception s) {
            System.out.println(s);
        }
    }

    public void makeRightImage() {
        try {
            GTUtil.deleteFileSafely(Paths.get(outputFolderForRight));
            Files.createDirectories(Paths.get(outputFolderForRight));

            int frame = 1;

            BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<Integer>((int) getTotalOfFileForRight());
            for (Integer i = 0; i < getTotalOfFileForRight(); i++) {
                blockingDeque.add(frame);
                frame++;
            }

            java.util.List<Thread> threads = new ArrayList<>();

            for (int i = 0; i < numberOfThreads; i++) {
                Thread thread = new Thread() {
                    public void run() {
                        Integer frame = 0;
                        while ((frame = blockingDeque.poll()) != null) {
                            makeRightImageFrame(frame);
                        }
                    }
                };
                threads.add(thread);
                thread.start();
            }

            for (int i = 0; i < threads.size(); i++) {
                Thread thread = threads.get(i);
                thread.join();
            }
        } catch (Exception ex) {
        }

//
//        for (long i = 0; i < getTotalOfFileForRight(); i++) {
//            try {
//                makeRightImageFrame(frame);
//
//                frame++;
//            } catch (Exception s) {
//                System.out.println(s);
//            }
//        }
    }

    private void combineFrame(int frame) {
        Image img1 = null;
        Image img2 = null;
        String fileName = frame + ".png";

        try {
            File file = new File(outputFolderForLeft + fileName);
            if (file.exists()) {
                try {
                    img1 = ImageIO.read(new File(outputFolderForLeft + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            File file = new File(outputFolderForRight + fileName);
            if (file.exists()) {
                try {
                    img2 = ImageIO.read(new File(outputFolderForRight + fileName));
                } catch (IOException e) {
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        BufferedImage img = combineImageEx(img1, img2);

        File outputFile = new File(outPutCombineFollder + fileName);

//        System.out.println("saving " + fileName);
        try {
            ImageIO.write(img, "PNG", outputFile);
        } catch (Exception s) {
            System.out.println(s);
        }
    }

    private void processCombine() {

        try {
            GTUtil.deleteFileSafely(Paths.get(outPutCombineFollder));
            Files.createDirectories(Paths.get(outPutCombineFollder));
        } catch (Exception ex) {
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
        BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<Integer>((int) n);
        for (Integer i = 1; i <= n; i++) {
            blockingDeque.add(i);
        }

        try {

            java.util.List<Thread> threads = new ArrayList<>();

            for (int i = 0; i < numberOfThreads; i++) {
                Thread thread = new Thread() {
                    public void run() {
                        Integer frame = 0;
                        while ((frame = blockingDeque.poll()) != null) {
                            combineFrame(frame);
                        }
                    }
                };
                threads.add(thread);
                thread.start();
            }

            for (int i = 0; i < threads.size(); i++) {
                Thread thread = threads.get(i);
                thread.join();
            }
        } catch (Exception ex) {
        }

//        for (long i = 1; i <= n; i++) {
//            combineFrame((int)i);
//        }
    }

    private BufferedImage combineImageEx(Image leftImg, Image rightImg) {
        BufferedImage bi = new BufferedImage(640, 480, BufferedImage.TYPE_INT_ARGB);

        Graphics gc = bi.createGraphics();

        gc.drawImage(leftImg, 0, 0, null);
        gc.drawImage(rightImg, 321, 0, null);
        return bi;
    }

    public void start(String videoFolderForLeft, String annotationFolderForLeft, String motionFolderForLeft,
                      String videoFolderForRight, String annotationFolderForRight, String motionFolderForRight) {
        try {
            this.videoFolderForLeft = videoFolderForLeft;
            this.annotationFolderForLeft = annotationFolderForLeft;
            this.motionFolderForLeft = motionFolderForLeft;

            this.videoFolderForRight = videoFolderForRight;
            this.annotationFolderForRight = annotationFolderForRight;
            this.motionFolderForRight = motionFolderForRight;

            Thread thread1 = new Thread() {
                public void run() {
                    makeLeftImage();
                }
            };
            thread1.start();

            Thread thread2 = new Thread() {
                public void run() {
                    makeRightImage();
                }
            };
            thread2.start();

            thread1.join();
            thread2.join();

            processCombine();
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
}
