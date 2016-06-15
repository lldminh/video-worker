package com.golftec.teaching.model.lesson;

import com.golftec.teaching.model.data.SwingMeta;
import com.golftec.teaching.model.types.MotionDataType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Swings belong to Students. To add a swing, you must have a Student.
 *
 * @author Al Wells
 */
public class Swing implements Serializable {

    private String videoUri;
    private String thumbnailUri;
    private Atif atif;
    private Map<MotionDataType, List<MotionData>> motionDataMap;
    //private DominantHand dominantHand;
    private SwingMeta swingMetadata;
    public Swing() {
        motionDataMap = new ConcurrentHashMap<MotionDataType, List<MotionData>>();
        List<MotionData> hb = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.HB, hb);
        List<MotionData> htlt = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.HTLT, htlt);
        List<MotionData> htrn = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.HTRN, htrn);
        List<MotionData> shb = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.SHB, shb);
        List<MotionData> shs = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.SHS, shs);
        List<MotionData> shtl = new ArrayList<MotionData>();
        motionDataMap.put(MotionDataType.SHTLT, shtl);

       // dominantHand = DominantHand.Right; // default to right handed
    }

    public Map<MotionDataType, List<MotionData>> getMotionDataMap() {
        return motionDataMap;
    }
    
    public SwingMeta getSwingMeta() {
    	return swingMetadata;
    }
    
    public void setSwingMeta(SwingMeta swingMetadata) {
    	this.swingMetadata = swingMetadata;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public Atif getAtif() {
        return atif;
    }

    public void setAtif(Atif atif) {
        this.atif = atif;
    }

    public List<MotionData> getMotionData(MotionDataType type) {
        return motionDataMap.get(type);
    }

    public void addMotionData(MotionData motionData) {
        List<MotionData> data = motionDataMap.get(motionData.getType());
        data.add(motionData);
    }

    public List<MotionData> getMotionData(int frameNumber) {
        List<MotionData> motionData = new ArrayList<MotionData>();
        if (motionData.size() == 0) {
            return motionData;
        }
        List<MotionData> data = motionDataMap.get(MotionDataType.HB);
        motionData.add(getMotionDataPoint(frameNumber, data));
        data = motionDataMap.get(MotionDataType.HTLT);
        motionData.add(getMotionDataPoint(frameNumber, data));
        data = motionDataMap.get(MotionDataType.HTRN);
        motionData.add(getMotionDataPoint(frameNumber, data));
        data = motionDataMap.get(MotionDataType.SHB);
        motionData.add(getMotionDataPoint(frameNumber, data));
        data = motionDataMap.get(MotionDataType.SHS);
        motionData.add(getMotionDataPoint(frameNumber, data));
        data = motionDataMap.get(MotionDataType.SHTLT);
        motionData.add(getMotionDataPoint(frameNumber, data));
        return motionData;
    }

    private MotionData getMotionDataPoint(int frameNumber, List<MotionData> motionData) {
        MotionData data = null;
        for (MotionData md : motionData) {
            if (md.getFrame() == frameNumber) {
                data = md;
                break;
            }
        }
        return data;
    }

}
