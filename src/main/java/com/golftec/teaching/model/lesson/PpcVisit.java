package com.golftec.teaching.model.lesson;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by hoang on 2015-09-10.
 */
public class PpcVisit {

    @SerializedName("notes")
    public String notes = "";

    @SerializedName("telestrations")
    public int telestrations;

    @SerializedName("drills")
    public int drills;
    /**
     * TODO: copy from lastVisit, should migrate to using real date object
     */
    @SerializedName("lastVisitDate")
    public Date lastVisitDate = DateTime.now().toDate();

    @Deprecated
    @SerializedName("lastVisit")
    private String lastVisit = "";

    public PpcVisit(String notes, int telestrations, int drills, String lastVisit) {
        this.notes = notes;
        this.telestrations = telestrations;
        this.drills = drills;
        this.setLastVisit(Strings.nullToEmpty(lastVisit));
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
        // also map to lastVisitDate
        if (Strings.isNullOrEmpty(this.lastVisit)) {
            this.lastVisitDate = null;
        } else {
            DateTime dateTime = GTUtil.parseDateTimeSafely(this.lastVisit, GTConstants.MAIN_JSON_DATETIME_FORMATTER);
            if (dateTime != null) {
                this.lastVisitDate = dateTime.toDate();
            }
        }
    }
}
