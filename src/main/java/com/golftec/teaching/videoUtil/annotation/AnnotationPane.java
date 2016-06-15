package com.golftec.teaching.videoUtil.annotation;

import com.golftec.teaching.videoUtil.drawingTool.GTErase;
import com.golftec.teaching.videoUtil.drawingTool.GTShape;
import com.golftec.teaching.videoUtil.drawingTool.IToolable;
import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.AnnotationStandByData;
import com.golftec.teaching.videoUtil.util.GTColor;
import com.golftec.teaching.videoUtil.util.GTMouseEvent;
import com.golftec.teaching.videoUtil.util.MouseEventType;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;

public class AnnotationPane {

    int FPS = 30;
    double width = 320;
    double height = 480;
    Canvas mainCanvas = null;
    Canvas drawingCanvas = null;
    Canvas highLightCanvas = null;
    Timeline timeline = null;
    Pane mainPane = null;
    IToolable iToolable = null;
    GTColor color = GTColor.GREEN;
    DrawingLayer drawingLayer = null;
    HighLightLayer highLightLayer = null;
    MainLayer mainLayer = null;
    long frame = 0;
    AnnotationHistory annotationHistory = null;
    boolean isRecording = false;
    private Map imgList = null;
    private final EventHandler<ActionEvent> nextFrame = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent t) {
            Image image = getSnapShot();

            frame++;
            System.out.println("adding annotation " + frame + ".png to disk");
//            saveImageIntoDisk(image, srcFrame);
//
            imgList.put(frame, image);
        }
    };


    private void saveImageIntoDisk(Image image, long frame) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    File outputFile = new File(exportFolder + frame + ".png");

                    try {
                        System.out.println("saving annotation " + frame + ".png to disk");
                        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
                    } catch (Exception s) {
                        System.out.println(s);
                    }
                });
            }
        };
        timerTask.run();
    }

    private String exportFolder = "";

    public void clearImage() {
        imgList = null;
    }

    public AnnotationPane(Pane mainPane, Canvas mainCanvas, Canvas drawingCanvas,
                          Canvas highLightCanvas) {
        this.mainCanvas = mainCanvas;
        this.drawingCanvas = drawingCanvas;
        this.highLightCanvas = highLightCanvas;
        this.mainPane = mainPane;
        this.annotationHistory = new AnnotationHistory(this);

        init();
    }

    public AnnotationPane() {
        this.annotationHistory = new AnnotationHistory(this);
        this.mainCanvas = new Canvas(width, height);
        this.drawingCanvas = new Canvas(width, height);
        this.highLightCanvas = new Canvas(width, height);
        this.mainPane = new Pane();

        this.mainPane.getChildren().add(mainCanvas);
        this.mainPane.getChildren().add(drawingCanvas);
        this.mainPane.getChildren().add(highLightCanvas);
        init();
    }

    public AnnotationPane(Pane mainPane) {
        this.annotationHistory = new AnnotationHistory(this);
        this.mainCanvas = new Canvas(width, height);
        this.drawingCanvas = new Canvas(width, height);
        this.highLightCanvas = new Canvas(width, height);
        this.mainPane = mainPane;

        this.mainPane.getChildren().add(mainCanvas);
        this.mainPane.getChildren().add(drawingCanvas);
        this.mainPane.getChildren().add(highLightCanvas);

        init();
    }

    public AnnotationHistory getAnnotationHistory() {
        return this.annotationHistory;
    }

    public void cancelShowingHistory() {
        this.annotationHistory.cancelShowHistory();
    }

    public Pane getMainPane() {
        return this.mainPane;
    }

    public MainLayer getMainLayer() {
        return this.mainLayer;
    }

    public HighLightLayer getHighLightLayer() {
        return this.highLightLayer;
    }

    public DrawingLayer getDrawingLayer() {
        return this.drawingLayer;
    }

    public void refresh() {
        this.mainLayer.refresh();
    }

    public void back() {
        this.mainLayer.back();
    }

    public void startExportImage(String exportFolder) {
        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }

        this.exportFolder = exportFolder;
        timeline.playFromStart();
    }

    public void stopExportImage() {
        timeline.stop();
    }

    public void showHistory(AnnotationHistory annotationHistory) {
        this.annotationHistory = annotationHistory;
        annotationHistory.setAnnotationPane(this);
        this.annotationHistory.showHistory();
    }

    public void saveSnapshotIntoDisk() {
        File file = new File(exportFolder);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            try {
                FileUtils.deleteDirectory(file);
                file.mkdirs();
            } catch (Exception ex) {
            }
        }

        stopExportImage();
        for (long frame = 1; frame < imgList.size(); frame++) {
            WritableImage image = (WritableImage) imgList.get(frame);
            File outputFile = new File(exportFolder + frame + ".png");

            try {
                System.out.println("saving annotation " + frame + ".png to disk");
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", outputFile);
            } catch (Exception s) {
                System.out.println(s);
            }
        }

        imgList = null;
    }

    public void init() {
        imgList = new HashMap();
        drawingLayer = new DrawingLayer(drawingCanvas);
        drawingLayer.setMainLayer(this.mainLayer);

        highLightLayer = new HighLightLayer(this.highLightCanvas);

        mainLayer = new MainLayer(mainCanvas);
        mainLayer.setHighLightLayer(this.highLightLayer);

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        double duration = 1000 / FPS;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(duration), nextFrame));

        mainPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeMouseEvent(mouseEvent, MouseEventType.MOUSE_PRESS);
            }
        });

        mainPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeMouseEvent(mouseEvent, MouseEventType.MOUSE_DRAGGED);
            }
        });

        mainPane.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeMouseEvent(mouseEvent, MouseEventType.MOUSE_MOVED);
            }
        });

        mainPane.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeMouseEvent(mouseEvent, MouseEventType.MOUSE_DRAG_RELEASED);
            }
        });

        mainPane.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                GTMouseEvent mouseEvent = new GTMouseEvent(me);
                consumeMouseEvent(mouseEvent, MouseEventType.MOUSE_RELEASED);
            }
        });
    }

    public void startRecord() {
        this.annotationHistory.startRecord();
        this.isRecording = true;
    }

    public void stopRecordAndSave(String filePath, String fileName) {
        this.isRecording = false;
        this.annotationHistory.serialize(filePath, fileName);
    }

    public Canvas getMainCanvas() {
        return this.mainCanvas;
    }

    public void setMainCanvas(Canvas mainCanvas) {
        this.mainCanvas = mainCanvas;
    }

    public Canvas getDrawingCanvas() {
        return this.drawingCanvas;
    }

    public void setDrawingCanvas(Canvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
    }

    public Canvas getHighLightCanvas() {
        return this.highLightCanvas;
    }

    public void setHighLightCanvas(Canvas highLightCanvas) {
        this.highLightCanvas = highLightCanvas;
    }

    public IToolable getiToolable() {
        return this.iToolable;
    }

    public void setiToolable(IToolable iToolable) {
        this.iToolable = iToolable;
        if (this.iToolable != null) {
            if (this.iToolable instanceof GTShape) {
                ((GTShape) this.iToolable).setGc(drawingCanvas.getGraphicsContext2D());
                ((GTShape) this.iToolable).setStroke(this.color);
            }
        }
    }

    public GTColor getColor() {
        return this.color;
    }

    public void setColor(GTColor gtColor) {
        this.color = gtColor;
//        switch (gtColor) {
//            case BLUE: {
//                this.color = Color.BLUE;
//                break;
//            }
//            case GREEN: {
//                this.color = Color.GREEN;
//                break;
//            }
//            case RED: {
//                this.color = Color.RED;
//                break;
//            }
//            case YELLOW: {
//                this.color = Color.YELLOW;
//                break;
//            }
//        }
        if (iToolable instanceof GTShape) {
            ((GTShape) this.iToolable).setStroke(this.color);
        }
    }

    public List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> getCurrentShapesEx() {
        List<com.golftec.teaching.videoUtil.drawingToolEx.GTShape> res = new ArrayList();
        List<GTShape> list = new ArrayList();

        try {
            List<GTShape> list2 = mainLayer.getAll();
            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    list.add(list2.get(i).clone());
                }
            }
        } catch (Exception ex) {
        }

        try {
            GTShape shape = (GTShape) iToolable;
            if (shape != null) {
                GTShape clone = shape.clone();
                if (clone != null) {
                    list.add(clone);
                }
            }
        } catch (Exception ex) {
        }

        try {
            List<GTShape> list1 = highLightLayer.getAll();
            if (list1 != null) {
                for (int i = 0; i < list1.size(); i++) {
                    list.add(list1.get(i).clone());
                }
            }
        } catch (Exception ex) {
        }

        for (int i = 0; i < list.size(); i++) {
            res.add(list.get(i).getGTShapeEx());
        }

        return res;
    }

    public List<GTShape> getCurrentShapes() {

        List<GTShape> list = new ArrayList();

        try {
            List<GTShape> list2 = mainLayer.getAll();
            if (list2 != null) {
                for (int i = 0; i < list2.size(); i++) {
                    list.add(list2.get(i).clone());
                }
            }
        } catch (Exception ex) {
        }

        try {
            GTShape shape = (GTShape) iToolable;
            if (shape != null) {
                GTShape clone = shape.clone();
                if (clone != null) {
                    list.add(clone);
                }
            }
        } catch (Exception ex) {
        }

        try {
            List<GTShape> list1 = highLightLayer.getAll();
            if (list1 != null) {
                for (int i = 0; i < list1.size(); i++) {
                    list.add(list1.get(i).clone());
                }
            }
        } catch (Exception ex) {
        }

        return list;
    }

    public Image getSnapShot() {
        double width = drawingCanvas.getWidth();
        double height = drawingCanvas.getHeight();

        Canvas canvas = new Canvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setWidth(width);
        canvas.setHeight(height);

        if (width == 0) {
            width = 320;
        }

        if (height == 0) {
            height = 480;
        }

        WritableImage wImage = new WritableImage((int) width, (int) height);

        WritableImage wImage1 = new WritableImage((int) width, (int) height);
        WritableImage wImage2 = new WritableImage((int) width, (int) height);
        WritableImage wImage3 = new WritableImage((int) width, (int) height);

        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        highLightCanvas.snapshot(sp, wImage1);
        drawingCanvas.snapshot(sp, wImage2);
        mainCanvas.snapshot(sp, wImage3);

        gc.drawImage(wImage1, 0, 0);
        gc.drawImage(wImage2, 0, 0);
        gc.drawImage(wImage3, 0, 0);

        wImage = canvas.snapshot(sp, null);

        return wImage;
    }

    public void standByHistory() {
        this.annotationHistory.pause();
//        this.annotationHistory.setStatus((byte) 1);
    }

    public void addStandByHistory() {
        AnnotationStandByData annotationStandByData = new AnnotationStandByData();

        List<GTShape> list = this.mainLayer.getAll();

        annotationStandByData.setGtShapeList(list);
//        for (int i = 0; i < list.size(); i++) {
//            annotationStandByData.gtShapeList.add(list.get(i));
//        }

        annotationHistory.addStandby(annotationStandByData);
    }

    public void continueHistory() {
        this.annotationHistory.resumeHistory();
//        this.annotationHistory.setStatus((byte) 0);
    }

    public void consumeStandByEvent(AnnotationStandByData annotationStandByData) {
        mainLayer.refresh();
        List<GTShape> gtShapeList = annotationStandByData.getGtShapeList();

        for (int i = 0; i < gtShapeList.size(); i++) {
            GTShape gtShape = gtShapeList.get(i);
            mainLayer.pushShape(gtShape);
        }
    }

    public void newHistory() {
        this.annotationHistory.dispose();
        this.annotationHistory = new AnnotationHistory(this);
    }

    public void cleanHistory() {
        this.annotationHistory.cleanHistory();
    }

    public void consumeMouseEvent(GTMouseEvent mouseEvent, MouseEventType mouseEventType) {
        if (this.isRecording) {
            AnnotationEvent annotationEvent = new AnnotationEvent();
            annotationEvent.mouseEvent = mouseEvent;
            annotationEvent.mouseEventType = mouseEventType;
            this.annotationHistory.add(annotationEvent);
        }

        switch (mouseEventType) {
            case MOUSE_DRAG_RELEASED: {
                if (iToolable != null) {
                    iToolable.consumeMouseDragReleased(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }

            case MOUSE_DRAGGED: {
                if (iToolable != null) {
                    drawingLayer.clear();
                    iToolable.consumeMouseDragged(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }

            case MOUSE_MOVED: {
                if (iToolable instanceof GTErase) {
                    //fire event on Main Canvas Board
                    mainLayer.consumeMouseMoved(mouseEvent);
                }

                if (iToolable != null) {
                    drawingLayer.clear();
                    iToolable.consumeMouseMoved(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }

            case MOUSE_PRESS: {
                if (iToolable != null) {
                    iToolable.consumeMousePress(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                        }
                    }
                }
                break;
            }

            case MOUSE_RELEASED: {
                if (iToolable != null) {
                    iToolable.consumeMouseReleased(mouseEvent);
                    if (iToolable instanceof GTShape) {
                        if (((GTShape) iToolable).isFinished) {
                            mainLayer.pushShape((GTShape) iToolable);
                            iToolable = drawingLayer.createNewInstance(iToolable);
                            ((GTShape) iToolable).setStroke(this.color);
                        }
                    }
                }
                break;
            }
        }
    }
}
