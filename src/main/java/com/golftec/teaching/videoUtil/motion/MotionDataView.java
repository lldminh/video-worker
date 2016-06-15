package com.golftec.teaching.videoUtil.motion;

import com.golftec.teaching.model.types.MotionDataType;
import com.golftec.teaching.videoUtil.util.ToolType;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class MotionDataView {

    private static final long WIDTH = 75;
    private static final long HEIGHT = 75;

    private Pane pane = null;
    public Text line1 = new Text();
    public Text line2 = new Text();
    public Text line3 = new Text();
    private double positionX = 0;
    private double positionY = 0;
    private MotionDataType motionDataType = null;
    private double alpha = 1;
    private boolean isSelected = false;
    private MotionDataBoard motionDataBoard = null;
    private MotionDataBoardForServer motionDataBoardForServer = null;


    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean value) {
        this.isSelected = value;
    }

    public Pane getPane() {
        return this.pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public MotionDataType getMotionDataType() {
        return this.motionDataType;
    }

    public void setMotionDataType(MotionDataType motionDataType) {
        this.motionDataType = motionDataType;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public Point2D getPosition() {
        return new Point2D(positionX, positionY);
    }

    public MotionDataView(double x, double y, MotionDataType motionDataType) {

        this.positionX = x;
        this.positionY = y;
        pane = new Pane();
        pane.prefHeight(WIDTH);
        pane.prefWidth(HEIGHT);

        line1.setLayoutX(20);
        line1.setLayoutY(20);

        line2.setLayoutX(20);
        line2.setLayoutY(35);

        line3.setLayoutX(20);
        line3.setLayoutY(50);

        pane.getChildren().add(line1);
        pane.getChildren().add(line2);
        pane.getChildren().add(line3);

        pane.setLayoutX(this.positionX);
        pane.setLayoutY(this.positionY);
        this.motionDataType = motionDataType;
        registerDrag();
    }

    public void setMotionDataBoard(MotionDataBoard motionDataBoard){
        this.motionDataBoard = motionDataBoard;
    }

    public void setMotionDataBoardForServer(MotionDataBoardForServer motionDataBoardForServer){
        this.motionDataBoardForServer = motionDataBoardForServer;
    }

    private void registerDrag() {
        pane.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = pane.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("ImageView");
                db.setContent(content);
//                t.consume();
            }
        });

        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                if (motionDataBoard != null) {
                    motionDataBoard.getToolBoard().consumeMouseEvent(ToolType.EMPTY, null);
                }

                if (motionDataBoardForServer != null) {
                    motionDataBoardForServer.getToolBoard().consumeMouseEvent(ToolType.EMPTY, null);
                }
                t.consume();
            }
        });
    }

    public void setMotionData(MotionData motionData) {
        if (motionData.getMotionDataType() == motionDataType) {

            double g = motionData.getColor().getGreen();
            double r = motionData.getColor().getRed();
            double b = motionData.getColor().getBlue();

            Color color = Color.color(g, r, b, alpha);

            pane.setBackground(new Background(new BackgroundFill(color, null, null)));

            this.line1.setText(motionData.getMotionDataType().toString());
            this.line2.setText(Integer.toString(motionData.getValue()));
//            this.line3.setText(motion.type.toString());

        }
    }
}
