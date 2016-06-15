package com.golftec.teaching.model.data;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.server.networking.response.SyncAction;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class Drill {

    public Date created = DateTime.now().toDate();
    /**
     * timestamp is used to determined if we should sync this item with client or not.
     */
    public long timestamp = DateTime.now().getMillis();

    /**
     * syncAction is to determined if the item is new/updated/deleted
     */
    public SyncAction syncAction = SyncAction.AddNew;

    @SerializedName("drillId")
    public String id;

    @SerializedName("drillName")
    public String name;

    @SerializedName("drillUrl")
    public String url;

    @SerializedName("drillThumbnailUrl")
    public String thumbnailUrl;

    @SerializedName("drillDescription")
    public String description;

    @SerializedName("swing_type_list")
    public List<String> swingTypeIdList = Lists.newArrayList();

    @SerializedName("category_list")
    public List<String> categoryIdList = Lists.newArrayList();

    /**
     * size of the file at url, as bytes
     */
    public long fileSize;

    @SerializedName("timestampMetadata")
    public String timestampMetadata;

    public Drill(String id, String name, String url, String thumbnailUrl, String description) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
    }

    public Drill(String id, String name, String url, String thumbnailUrl, String description, String swingType, List<String> categoryIdList) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.swingTypeIdList.add(swingType);
        this.categoryIdList = categoryIdList;
    }

    public Drill() { }

    public static Drill copy(Drill original) {
        Drill drill = new Drill();
        drill.id = original.id;
        drill.name = original.name;
        drill.url = original.url;
        drill.thumbnailUrl = original.thumbnailUrl;
        drill.description = original.description;
        drill.created = original.created;
        drill.fileSize = original.fileSize;
        drill.timestamp = original.timestamp;
        drill.timestampMetadata = original.timestampMetadata;
        drill.syncAction = original.syncAction;
        drill.swingTypeIdList = original.swingTypeIdList;
        drill.categoryIdList = original.categoryIdList;
        return drill;
    }

    public String metadataTimestamp() {
        String format = String.format                                                                                                               ("id=%s,name=%s,url=%s,thumbnailUrl=%s,description=%s,created=%s,fileSize=%d,timestamp=%d",
                                      this.id, this.name, this.url, this.thumbnailUrl, this.description, this.created, this.fileSize, this.timestamp);
        if (swingTypeIdList != null) {
            format += "swingTypeIdList=[";
            for (String s : swingTypeIdList) {
                format += s + ",";
            }
            format += "]";
        }
        if (categoryIdList != null) {
            format += "categoryIdList=[";
            for (String s : categoryIdList) {
                format += s;
            }
            format += "]";
        }
        return GTUtil.md5Hex(format);
    }
}
