package com.golftec.teaching.videoUtil.motion.control;

import com.golftec.teaching.videoUtil.motion.MotionDataView;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import com.golftec.teaching.model.types.MotionDataType;

public class MotionSidePane extends VBox {

    public Pane SHS;
    public Text SHSLine2;
    public Text SHSLine1;

    public Pane SHTLT;
    public Text SHTLTLine1;
    public Text SHTLTLine2;

    public Pane SHB;
    public Text SHBLine1;
    public Text SHBLine2;

    public Pane HTRN;
    public Text HTRNLine1;
    public Text HTRNLine2;

    public Pane HTLT;
    public Text HTLTLine1;
    public Text HTLTLine2;

    public Pane HB;
    public Text HBLine1;
    public Text HBLine2;

    private IMotionSidePane iMotionSidePane = null;

    public MotionDataView motionViewSHS;
    public MotionDataView motionViewSHTLT;
    public MotionDataView motionViewSHB;
    public MotionDataView motionViewHTRN;
    public MotionDataView motionViewHTLT;
    public MotionDataView motionViewHB;


    private void registerDrag() {
        HB.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = HB.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("HB");
                db.setContent(content);
                iMotionSidePane.onHB_DragDetected();
                t.consume();
            }
        });

        HTLT.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = HTLT.startDragAndDrop(TransferMode.ANY);
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("HTLT");
                db.setContent(content);
                iMotionSidePane.onHTLT_DragDetected();
                t.consume();
            }
        });

        HTRN.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = HTRN.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("HTRN");
                db.setContent(content);
                iMotionSidePane.onHTRN_DragDetected();
                t.consume();
            }
        });

        SHS.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = SHS.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("SHS");
                db.setContent(content);
                iMotionSidePane.onSHS_DragDetected();
                t.consume();
            }
        });

        SHB.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = SHB.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("SHB");
                db.setContent(content);
                iMotionSidePane.onSHB_DragDetected();
                t.consume();
            }
        });


        SHTLT.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent t) {
                Dragboard db = SHTLT.startDragAndDrop(TransferMode.ANY);

                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
                content.putString("SHTLT");
                db.setContent(content);
                iMotionSidePane.onSHTLT_DragDetected();
                t.consume();
            }
        });
    }

    public MotionSidePane(IMotionSidePane iMotionSidePane) {
        this.iMotionSidePane = iMotionSidePane;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/motionSidePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void init() {
        motionViewSHS = new MotionDataView(0, 0, MotionDataType.SHS);
        motionViewSHS.setPane(SHB);
        motionViewSHS.line1 = SHSLine1;
        motionViewSHS.line2 = SHSLine2;

        motionViewSHTLT = new MotionDataView(0, 0, MotionDataType.SHTLT);
        motionViewSHTLT.setPane(SHTLT);
        motionViewSHTLT.line1 = SHTLTLine1;
        motionViewSHTLT.line2 = SHTLTLine2;

        motionViewSHB = new MotionDataView(0, 0, MotionDataType.SHB);
        motionViewSHB.setPane(SHB);
        motionViewSHB.line1 = SHBLine1;
        motionViewSHB.line2 = SHBLine2;

        motionViewHTRN = new MotionDataView(0, 0, MotionDataType.HTRN);
        motionViewHTRN.setPane(HTRN);
        motionViewHTRN.line1 = HTRNLine1;
        motionViewHTRN.line2 = HTRNLine2;

        motionViewHTLT = new MotionDataView(0, 0, MotionDataType.HTLT);
        motionViewHTLT.setPane(HTLT);
        motionViewHTLT.line1 = HTLTLine1;
        motionViewHTLT.line2 = HTLTLine2;

        motionViewHB = new MotionDataView(0, 0, MotionDataType.HB);
        motionViewHB.setPane(HB);
        motionViewHB.line1 = HBLine1;
        motionViewHB.line2 = HBLine2;

        registerDrag();
    }
}
