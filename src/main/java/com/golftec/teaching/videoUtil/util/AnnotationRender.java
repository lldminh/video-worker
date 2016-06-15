package com.golftec.teaching.videoUtil.util;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.videoUtil.drawingToolEx.GTCircle;
import com.golftec.teaching.videoUtil.drawingToolEx.GTLine;
import com.golftec.teaching.videoUtil.drawingToolEx.GTShape;
import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.AnnotationStandByData;
import com.google.common.collect.Maps;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Teo on 2015-07-12.
 */
public class AnnotationRender {

    private AnnotationHistory annotationHistory = null;
    private String snapshotAnnotationFolder = "snapshot/left/annotation/";
    private int numberOfThreads = 7;

    public AnnotationHistory getAnnotationHistory() {
        return this.annotationHistory;
    }

    public void setAnnotationHistory(AnnotationHistory annotationHistory) {
        this.annotationHistory = annotationHistory;
    }

    public String getSnapshotAnnotationFolder() {
        return this.snapshotAnnotationFolder;
    }

    public void setSnapshotAnnotationFolder(String folder) {
        this.snapshotAnnotationFolder = folder;
    }

    public void start() {
//        createSimulateData();
        try {
            makePNGImages();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public void setNumberOfThreads(int numberOfThreads){
        this.numberOfThreads = numberOfThreads;
    }

    public BufferedImage makeBufferImage(List<GTShape> list) {
        BufferedImage bufferedImage = new BufferedImage(320, 480, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gc = bufferedImage.createGraphics();
        for (int i = 0; i < list.size(); i++) {
            GTShape gtShape = list.get(i);
            gtShape.draw(gc);
//            gtShape.print();
        }
        return bufferedImage;
    }

    public void makePNGImages() {
        if (this.annotationHistory != null) {
            if (this.annotationHistory.frameMap != null) {
                try {
                    GTUtil.deleteFileSafely(Paths.get(snapshotAnnotationFolder));
                    Files.createDirectories(Paths.get(snapshotAnnotationFolder));

                    Map map = this.annotationHistory.frameMap;
                    Iterator it = map.entrySet().iterator();

                    BlockingQueue<AnnotationStandByData> blockingDeque =
                            new ArrayBlockingQueue<>(map.size());

                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry) it.next();
                        AnnotationStandByData annotationStandByData = (AnnotationStandByData) pair.getValue();

//                    makePNGImageByFrame(annotationStandByData);
                        blockingDeque.add(annotationStandByData);
                    }

                    java.util.List<Thread> threads = new ArrayList<>();

                    for (int i = 0; i < numberOfThreads; i++) {
                        Thread thread = new Thread() {
                            public void run() {
                                AnnotationStandByData annotationStandByData = null;
                                while ((annotationStandByData = blockingDeque.poll()) != null) {
                                    makePNGImageByFrame(annotationStandByData);
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
            }
        }
    }

    private void makePNGImageByFrame(AnnotationStandByData annotationStandByData) {
        List<GTShape> list = annotationStandByData.getGtShapeExList();

        File file = new File(snapshotAnnotationFolder + annotationStandByData.getDesFrame() + ".png");
//        System.out.println("making Annotation PNG, frame: " + annotationStandByData.getDesFrame() + ", size: " + list.size());
        makePNGImage(list, file);
    }

    private void makePNGImage(List<GTShape> list, File file) {
        try {
            BufferedImage bufferedImage = makeBufferImage(list);
            ImageIO.write(bufferedImage, "PNG", file);
        } catch (Exception ex) {
            System.out.println("ERROR when making PNG Image");
        }
    }

    private void createSimulateData() {
        this.annotationHistory.frameMap = Maps.newHashMap();

        try {
            AnnotationStandByData annotationStandByData = new AnnotationStandByData();
            annotationStandByData.setDesFrame(1);

            List<GTShape> list = new ArrayList();
            GTShape gtShape = new GTLine(30, 30, 100, 100);
            list.add(gtShape);

            annotationStandByData.setGtShapeExList(list);

            this.annotationHistory.frameMap.put(annotationStandByData.getDesFrame(), annotationStandByData);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            AnnotationStandByData annotationStandByData = new AnnotationStandByData();
            annotationStandByData.setDesFrame(2);

            List<GTShape> list = new ArrayList();
            GTShape gtShape = new GTLine(30, 30, 200, 200);
            list.add(gtShape);

            annotationStandByData.setGtShapeExList(list);

            this.annotationHistory.frameMap.put(annotationStandByData.getDesFrame(), annotationStandByData);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            AnnotationStandByData annotationStandByData = new AnnotationStandByData();
            annotationStandByData.setDesFrame(3);

            List<GTShape> list = new ArrayList();
            GTShape gtShape1 = new GTLine(30, 30, 200, 200);
            GTShape gtShape2 = new GTCircle(30, 30, 50);
            list.add(gtShape1);
            list.add(gtShape2);

            annotationStandByData.setGtShapeExList(list);

            this.annotationHistory.frameMap.put(annotationStandByData.getDesFrame(), annotationStandByData);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            AnnotationStandByData annotationStandByData = new AnnotationStandByData();
            annotationStandByData.setDesFrame(4);

            List<GTShape> list = new ArrayList();
            GTShape gtShape1 = new GTLine(30, 30, 300, 300);
            GTShape gtShape2 = new GTCircle(40, 40, 50);
            list.add(gtShape1);
            list.add(gtShape2);

            annotationStandByData.setGtShapeExList(list);

            this.annotationHistory.frameMap.put(annotationStandByData.getDesFrame(), annotationStandByData);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
