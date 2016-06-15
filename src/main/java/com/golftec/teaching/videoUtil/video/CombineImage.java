package com.golftec.teaching.videoUtil.video;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Created by sonleduc on 3/1/15.
 */
public class CombineImage {
    private javafx.scene.image.Image leftImage = null;
    private javafx.scene.image.Image rightImage = null;

    private static final int WIDTH = 620;
    private static final int HEIGHT = 600;

    private static final int DISTANCE = 20;

    private int width = WIDTH;
    private int height = HEIGHT;


    public CombineImage() {
    }

    public void setLeftImage(javafx.scene.image.Image image) {
        this.leftImage = image;
    }

    public void setRightImage(javafx.scene.image.Image image) {
        this.rightImage = rightImage;
    }

    public javafx.scene.image.Image getLeftImage() {
        return this.leftImage;
    }

    public javafx.scene.image.Image getRightImage() {
        return this.rightImage;
    }

    public javafx.scene.image.Image getCombineImage() {

        File file1 = new File("image1.jpg");
        this.leftImage = new javafx.scene.image.Image(file1.toURI().toString());

        File file2 = new File("image2.jpg");
        this.rightImage = new javafx.scene.image.Image(file2.toURI().toString());

        WritableImage wImage = new WritableImage(WIDTH, HEIGHT);
        PixelWriter pixelWriter = wImage.getPixelWriter();

        PixelReader pixelReader1 = this.leftImage.getPixelReader();
        PixelReader pixelReader2 = this.rightImage.getPixelReader();

        // Determine the color of each pixel in a specified row
        for (int readY = 0; readY < leftImage.getHeight(); readY++) {
            for (int readX = 0; readX < leftImage.getWidth(); readX++) {
                Color color = pixelReader1.getColor(readX, readY);
//                System.out.println("\nPixel color at coordinates (" +
//                        readX + "," + readY + ") "
//                        + color.toString());
//                System.out.println("R = " + color.getRed());
//                System.out.println("G = " + color.getGreen());
//                System.out.println("B = " + color.getBlue());
//                System.out.println("Opacity = " + color.getOpacity());
//                System.out.println("Saturation = " + color.getSaturation());

                // Now write a brighter color to the PixelWriter.
//                color = color.brighter();
                pixelWriter.setColor(readX, readY, color);
            }
        }

        for (int readY = 0; readY < rightImage.getHeight(); readY++) {
            for (int readX = 0; readX < rightImage.getWidth(); readX++) {
                Color color = pixelReader2.getColor(readX, readY);
//                System.out.println("\nPixel color at coordinates (" +
//                        readX + "," + readY + ") "
//                        + color.toString());
//                System.out.println("R = " + color.getRed());
//                System.out.println("G = " + color.getGreen());
//                System.out.println("B = " + color.getBlue());
//                System.out.println("Opacity = " + color.getOpacity());
//                System.out.println("Saturation = " + color.getSaturation());

                // Now write a brighter color to the PixelWriter.
//                color = color.brighter();
                pixelWriter.setColor(readX + (((WIDTH - DISTANCE) / 2) + DISTANCE), readY, color);
            }
        }

        File outputFile = new File("CanvasImage.png");


        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wImage, null), "png", outputFile);
        } catch (Exception s) {
        }


//        PixelWriter pixelWriter = leftImage.
        return null;
    }

}
