package com.golftec.teaching.model.data;

import com.golftec.teaching.server.networking.response.SyncAction;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;

public class TecCard {

    @SerializedName("tecCardId")
    public String id;

    @SerializedName("thumbnailUrl")
    public String thumbnailUrl;

    @SerializedName("pdfUrl")
    public String pdfUrl;

    public long pdfFileSize;
    public long thumbnailFileSize;
    public int version;

    public Date created = DateTime.now().toDate();

    /**
     * timestamp is used to determined if we should sync this item with client or not.
     */
    public long timestamp = DateTime.now().getMillis();

    /**
     * syncAction is to determined if the item is new/updated/deleted
     */
    public SyncAction syncAction = SyncAction.AddNew;

    public TecCard(String id, String thumbnailUrl, String pdfUrl) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.pdfUrl = pdfUrl;
    }

    public TecCard(String id, String thumbnailUrl, String pdfUrl, int version) {
        this.id = id;
        this.thumbnailUrl = thumbnailUrl;
        this.pdfUrl = pdfUrl;
        this.version = version;
    }

    public TecCard() { }

    public static TecCard copy(TecCard original) {
        TecCard tecCard = new TecCard();
        tecCard.id = original.id;
        tecCard.thumbnailUrl = original.thumbnailUrl;
        tecCard.pdfUrl = original.pdfUrl;
        tecCard.created = original.created;
        tecCard.thumbnailFileSize = original.thumbnailFileSize;
        tecCard.pdfFileSize = original.pdfFileSize;
        tecCard.timestamp = original.timestamp;
        tecCard.syncAction = original.syncAction;
        tecCard.version = original.version;
        return tecCard;
    }
}
