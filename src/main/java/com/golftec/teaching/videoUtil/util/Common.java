package com.golftec.teaching.videoUtil.util;

import com.golftec.teaching.videoUtil.drawingTool.*;
import com.golftec.teaching.videoUtil.history.*;
import com.golftec.teaching.videoUtil.motion.MotionData;
import com.golftec.teaching.videoUtil.motion.MotionDataSet;
import com.golftec.teaching.view.LessonView;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import com.golftec.teaching.model.types.MotionDataType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Common {

    static private Canvas motionCanvas = new Canvas(75, 75);
    static private GraphicsContext motionGC = motionCanvas.getGraphicsContext2D();

    static public String getTime(long milliseconds) {
        int seconds = (int) (milliseconds / 1000) % 60;
        int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
        int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);

        return formatMinute(minutes) + ":" + formatSecond(seconds);
    }

    static public void flipImage(String filePath) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
            BufferedImage bufferedImage = invertImage(img);
            File outputfile = new File(filePath);
            ImageIO.write(bufferedImage, "png", outputfile);
        } catch (IOException e) {
        }
    }

    static public Color getColor(GTColor gtColor) {
        Color color = null;
        switch (gtColor) {
            case BLUE: {
                color = Color.BLUE;
                break;
            }
            case RED: {
                color = Color.RED;
                break;
            }
            case GREEN: {
                color = Color.GREEN;
                break;
            }
            case YELLOW: {
                color = Color.YELLOW;
                break;
            }
        }
        return color;
    }

    static public java.awt.Color getAWTColor(GTColor gtColor) {
        java.awt.Color color = null;
        switch (gtColor) {
            case BLUE: {
                color = java.awt.Color.BLUE;
                break;
            }
            case RED: {
                color = java.awt.Color.RED;
                break;
            }
            case GREEN: {
                color = java.awt.Color.GREEN;
                break;
            }
            case YELLOW: {
                color = java.awt.Color.YELLOW;
                break;
            }
        }
        return color;
    }

    static public void saveImageToDisk(Image image) {
        File outputFile = new File("test.png");

        try {

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
        } catch (Exception s) {
            System.out.println(s);
        }
    }

    static public String formatTotalTime(double originMiliSeconds) {
        originMiliSeconds = originMiliSeconds / 1000;
        int seconds = (int) (originMiliSeconds) % 60;
        int minutes = (int) ((originMiliSeconds / 60) % 60);
        int hours = (int) ((originMiliSeconds / (60 * 60)) % 24);

        return formatMinute(minutes) + ":" + formatSecond(seconds);
    }

    public static BufferedImage invertImage(BufferedImage source) {
        BufferedImage buff = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics2D graphics = buff.createGraphics();
        graphics.drawImage(source, 0, 0, source.getWidth(), source.getHeight(),
                           source.getWidth(), 0, 0, source.getHeight(), null);
        graphics.dispose();
        return buff;
    }

    public static Image invertImage(Image source) {

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(source, null);

        BufferedImage bufferedImage1 = Common.invertImage(bufferedImage);

        WritableImage writableImage = new WritableImage((int) source.getWidth(), (int) source.getHeight());
        SwingFXUtils.toFXImage(bufferedImage1, writableImage);

//        Common.saveImageToDisk(writableImage);

        return writableImage;
    }

    static public String formatMinute(int minutes) {
        if (minutes < 10) {
            return "0" + minutes;
        }

        return Integer.toString(minutes);
    }

    static public String formatSecond(int seconds) {
        if (seconds < 10) {
            return "0" + seconds;
        }

        return Integer.toString(seconds);
    }

    static public String getFileNameFromPath(String path) {
        try {
            Path p = Paths.get(path);
            String file = p.getFileName().toString();
            return file;
        } catch (Exception ex) {
            return "";
        }
    }

    static public int getTotalFiles(String path) {
        try {
            File folder = new File(path);
            File[] listOfFiles = folder.listFiles();
            return listOfFiles.length;
        } catch (Exception ex) {
        }
        return 0;
    }

    static public long getTime(int frame, int FPS) {
        return (frame * 1000) / FPS; // in millisecond
    }

    static public MotionDataSet simulateData() {
        MotionDataSet motionDataSet = new MotionDataSet();
        for (int i = 0; i < 10000; i++) {
            Random random = new Random();

            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();

            Color randomColor = Color.color(g, r, b);

            int pick = new Random().nextInt(MotionDataType.values().length);

            motionDataSet.add(new MotionData(random.nextInt(1000), randomColor, MotionDataType.values()[pick], random.nextInt(1000)));
        }
        return motionDataSet;
    }

    static public int getIntRandom(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    static public void serialize(String filePath, String fileName, DrawingLayerHistory drawingLayerHistory) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(drawingLayerHistory);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in /tmp/drawingBoardHistory.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public void serialize(String filePath, String fileName, LessonView lessonView) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(lessonView);
            out.close();
            fileOut.close();

            System.out.printf("LessonView Serialized data is saved ");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public LessonView deserialize(String filePath, String fileName, LessonView lessonView) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            lessonView = (LessonView) in.readObject();
            in.close();
            fileIn.close();
            return lessonView;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("LessonView class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public DrawingLayerHistory deserialize(String filePath, String fileName, DrawingLayerHistory drawingLayerHistory) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            drawingLayerHistory = (DrawingLayerHistory) in.readObject();
            in.close();
            fileIn.close();
            return drawingLayerHistory;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("DrawingLayerHistory class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public void serialize(String filePath, String fileName, ToolBoardHistory toolBoardHistory) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(toolBoardHistory);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in /tmp/toolBoardHistory.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public ToolBoardHistory deserialize(String filePath, String fileName, ToolBoardHistory toolBoardHistory) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            toolBoardHistory = (ToolBoardHistory) in.readObject();
            in.close();
            fileIn.close();
            return toolBoardHistory;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("DrawingBoardHistory class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public void serialize(String filePath, String fileName, VideoHistory videoHistory) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(videoHistory);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in /tmp/videoHistory.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public VideoHistory deserialize(String filePath, String fileName, VideoHistory videoHistory) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            videoHistory = (VideoHistory) in.readObject();
            in.close();
            fileIn.close();
            return videoHistory;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("VideoHistory class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public void serialize(String filePath, String fileName, MotionHistory motionHistory) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(motionHistory);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in /tmp/motionHistory.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public MotionHistory deserialize(String filePath, String fileName, MotionHistory motionHistory) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            motionHistory = (MotionHistory) in.readObject();
            in.close();
            fileIn.close();
            return motionHistory;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("motionHistory class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public void serialize(String filePath, String fileName, AnnotationHistory annotationHistory) {
        try {
            String path = filePath;

            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }

            FileOutputStream fileOut =
                    new FileOutputStream(path + fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(annotationHistory);
            out.close();
            fileOut.close();

            System.out.printf("Serialized data is saved in /tmp/annotationHistory.ser");
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    static public AnnotationHistory deserialize(String filePath, String fileName, AnnotationHistory annotationHistory) {
        try {
            String path = filePath;
            FileInputStream fileIn = new FileInputStream(path + fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            annotationHistory = (AnnotationHistory) in.readObject();
            in.close();
            fileIn.close();
            return annotationHistory;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("AnnotationHistory class not found");
            c.printStackTrace();
            return null;
        }
    }

    static public Image getMotionImage(MotionData motionData) {

        motionGC.setFill(motionData.getColor());
        motionGC.fillRect(0, 0, motionCanvas.getWidth(), motionCanvas.getHeight());

        motionGC.setFill(Color.BLACK);

        motionGC.fillText(motionData.getMotionDataType().toString(), 30, 20);
        motionGC.fillText(Integer.toString(motionData.getValue()), 30, 40);

        WritableImage wImage = new WritableImage(75, 75);

        motionCanvas.snapshot(null, wImage);

//        File outputFile = new File(motion.srcFrame + ".png");
//
//        try {
//            ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", outputFile);
//        } catch (Exception s) {
//        }

        return wImage;
    }
}
