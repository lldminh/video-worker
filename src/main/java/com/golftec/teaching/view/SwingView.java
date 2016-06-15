package com.golftec.teaching.view;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.data.SwingMeta;
import com.golftec.teaching.model.lesson.MotionData;
import com.golftec.teaching.model.lesson.Swing;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by hoang on 5/22/15.
 */
public class SwingView implements Serializable {

    public String swingId = "";
    public String videoUri = "";

    //TODO: return this value for sync function
    public long videoFileSize = 0;

    public String thumbnailUri = "";
    public AtifView atif;
    public Map<Integer, List<MotionDataView2>> motionDataMap = Maps.newHashMap();
    public SwingMeta swingMetadata = new SwingMeta();

    /**
     * used in lesson-complete
     */
    public Date timestamp = DateTime.now().toDate();

    /**
     * To be used when uploading file to S3
     */
    public String videoLocalPath;

    public SwingView(Swing swing) {
        if (swing != null) {
            videoUri = Strings.nullToEmpty(swing.getVideoUri());
            swingId = GTUtil.md5Hex(videoUri); //TODO: may need actual id of a swing
            thumbnailUri = Strings.nullToEmpty(swing.getThumbnailUri());
            if (swing.getAtif() != null) {
                atif = new AtifView(swing.getAtif());
            }
            if (swing.getMotionDataMap() != null && swing.getMotionDataMap().size() > 0) {
                // get all motion-data obj as flat-map
                // convert to custom MotionDataView2 obj
                // group them by srcFrame (for iOS)
                motionDataMap = swing.getMotionDataMap().values().stream()
                                     .flatMap(list -> {
                                         Stream.Builder<MotionData> builder = Stream.builder();
                                         list.forEach(builder::accept);
                                         return builder.build();
                                     })
                                     .map(MotionDataView2::new)
                                     .collect(groupingBy(item -> item.frame));
            }
            if (swing.getSwingMeta() != null) {
                swingMetadata = swing.getSwingMeta();
            }
        }
    }

    public SwingView() {}

    public SwingView(String swingId, String videoUri, String thumbnailUri, AtifView atifView, Map<Integer, List<MotionDataView2>> motionDataMap, SwingMeta swingMeta) {
        this.swingId = swingId;
        this.videoUri = videoUri;
        this.thumbnailUri = thumbnailUri;
        this.atif = atifView;
        this.motionDataMap = motionDataMap;
        this.swingMetadata = swingMeta;
    }
}
