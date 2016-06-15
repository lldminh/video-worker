//package com.golftec.teaching.videoUtil.video;
//
//import javafx.event.EventHandler;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.ClipboardContent;
//import javafx.scene.input.Dragboard;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.input.TransferMode;
//
///**
// * Created by sonleduc on 3/12/15.
// */
//public class GTImageView {
//    public ImageView imageView = null;
//    private double positionX = 0;
//    private double positionY = 0;
//    private MotionData motion = null;
//    public MotionData.Type type = null;
//    public double anpha = 1;
//    public boolean isSelected = false;
//
//    public GTImageView(double x, double y, MotionData.Type type){
//        this.positionX = x;
//        this.positionY = y;
//        imageView = new ImageView();
//        imageView.prefHeight(75);
//        imageView.prefWidth(75);
//        imageView.setLayoutX(this.positionX);
//        imageView.setLayoutY(this.positionY);
//        this.type = type;
//        registerDrag();
//    }
//
//    private void registerDrag(){
//        imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent t) {
//                Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
//
//                /* put a string on dragboard */
//                ClipboardContent content = new ClipboardContent();
//                content.putString("ImageView");
//                db.setContent(content);
//
//                isSelected = true;
//                t.consume();
//            }
//        });
//    }
//
//    public void setMotionData(MotionData motion){
//        if(motion.type == type){
//            this.motion = motion;
//            imageView.setImage(motion.image);
//        }
//    }
//
//    public void setMotionData(Image image){
//        this.motion = motion;
//        imageView.setImage(image);
//    }
//}
