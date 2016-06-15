package com.golftec.teaching.view;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.data.SwingMeta;
import com.golftec.teaching.server.networking.response.SyncAction;
import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hoang on 5/23/15.
 */
public class ProVideoView {

    @SerializedName("proVideoId")
    public String id = "";
    public Date dateAdded = DateTime.now().toDate();
    public boolean isLeft = false;
    public boolean isRight = false;
    public boolean isFront = false;
    public boolean isSide = false;
    public AtifView atif = new AtifView();
    public Map<Integer, List<MotionDataView2>> motionDataMap = Maps.newHashMap();
    public String proName = "";
    public String videoUri = "";
    public String S3Url = "";
    public String checkSum="";
    /**
     * TODO: move this coach's preferences specific data to another class, probably ProVideoViewWithPreference
     */
    public boolean isPreferred = false;
    public boolean isHidden = false;
    public SwingMeta swingMetadata = new SwingMeta();
    public String thumbnailUri = "";

    /**
     * file-size of the resized video, in bytes
     */
    public long fileSize = 0;

    /**
     * timestamp is used to determined if we should sync this item with client or not.
     */
    public long timestamp = DateTime.now().getMillis();

    /**
     * syncAction is to determined if the item is new/updated/deleted
     */
    public SyncAction syncAction = SyncAction.AddNew;

    public ProVideoView() { }

    public static ProVideoView copy(ProVideoView original) {
        ProVideoView proVideoView = new ProVideoView();
        proVideoView.id = original.id;
        proVideoView.dateAdded = original.dateAdded;
        proVideoView.isLeft = original.isLeft;
        proVideoView.isRight = original.isRight;
        proVideoView.isFront = original.isFront;
        proVideoView.isSide = original.isSide;
        proVideoView.atif = original.atif;
        proVideoView.motionDataMap = original.motionDataMap;
        proVideoView.proName = original.proName;
        proVideoView.videoUri = original.videoUri;
        proVideoView.isPreferred = original.isPreferred;
        proVideoView.isHidden = original.isHidden;
        proVideoView.swingMetadata = original.swingMetadata;
        proVideoView.thumbnailUri = original.thumbnailUri;
        proVideoView.fileSize = original.fileSize;
        proVideoView.timestamp = original.timestamp;
        proVideoView.syncAction = original.syncAction;
        return proVideoView;
    }

    public String generateId() {
        String input = String.format("%s", videoUri);
        return GTUtil.md5Hex(input);
    }
}
