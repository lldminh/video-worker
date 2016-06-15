//package com.golftec.teaching.videoUtil.video;
//
//import javafx.geometry.Point2D;
//import javafx.scene.layout.AnchorPane;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by sonleduc on 3/12/15.
// */
//public class MotionBoard {
//    private AnchorPane pane = null;
//    private List<GTImageView> list = null;
//
//    public MotionBoard(AnchorPane pane) {
//        this.pane = pane;
//        this.list = new ArrayList<GTImageView>();
//    }
//
//    public void add(GTImageView gtImageView) {
//        this.list.add(gtImageView);
//        this.pane.getChildren().add(gtImageView.imageView);
//    }
//
//    public void show(MotionData motion) {
//        for (int i = 0; i < list.size(); i++) {
//            GTImageView gtImageView = list.get(i);
//            if (gtImageView.type == motion.type) {
//                gtImageView.setMotionData(motion);
//            }
//        }
//    }
//
//    public void moveTo(Point2D point2D){
//        for (int i = 0; i < list.size(); i++) {
//            GTImageView gtImageView = list.get(i);
//            if (gtImageView.isSelected) {
//                gtImageView.imageView.setLayoutX(point2D.getX());
//                gtImageView.imageView.setLayoutY(point2D.getY());
//            }
//        }
//    }
//
//    public void resetSelected(){
//        for (int i = 0; i < list.size(); i++) {
//            GTImageView gtImageView = list.get(i);
//            gtImageView.isSelected = false;
//        }
//    }
//
//    public void show(List<MotionData> motionDataList) {
//        if (motionDataList != null && motionDataList.size() > 0) {
//            for (int j = 0; j < motionDataList.size(); j++) {
//                MotionData motion = motionDataList.get(j);
//                for (int i = 0; i < list.size(); i++) {
//                    GTImageView gtImageView = this.list.get(i);
//                    if(motion.type !=null){
//                        if (gtImageView.type == motion.type) {
//                            gtImageView.setMotionData(motion);
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//
//}
