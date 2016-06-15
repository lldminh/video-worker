package com.golftec.teaching.model.lesson;

import com.golftec.teaching.common.GTConstants;
import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.DominantHand;
import com.golftec.teaching.model.Sex;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Students belong to Lessons. So to add a student, you must also have
 * a lesson.
 *
 * @author Al Wells
 */
public class Student implements Serializable {

    /**
     * Important: this is the id used by TSG to communicate with its clients (IPAD, desktop, ...), NOT the id in db.
     */
    public String studentId = "";
    /**
     * Important: this is the Id in database, mapped to golftec_new.Player.Id
     */
    public String studentIdGolftec = "";

    /**
     * Important: this is NOT studentId, or studentIdGolftec
     */
    public String wuci = "";

    public String firstName = "";
    public String lastName = "";
    public String email = "";

    public DateTime lastContacted = new DateTime();
    @SerializedName("phone")
    public String phoneNumber = "";
    public String streetOne = "";
    public String streetTwo = "";
    @SerializedName("zip")
    public String postalCode = "";
    public String city = "";
    public String state = "";
    public String country = "";

    public Sex gender = Sex.Male;
    /**
     * how did the student hear about golfTec
     */
    public String howHeard = "";
    public DominantHand handedness = DominantHand.Right;
    public String referringEmail = "";
    /**
     * 0 = no contact.
     * <p>
     * 1 = email contact.
     * <p>
     * 2 = phone contact.
     */
    public int mayContact = 0;

    /**
     * TODO: mapped to belongAt column in golftec_new.Player table. Not sure if I should use it for now
     */
    public String storeId = "";

    // begin - player_info
    public DateTime lastLessonDateTime;
    public String lastLessonType = "";
    /**
     * TODO: copy the planEndDate here, should migrate to using real date object
     */
    public Date planEndDateNew;
    public List<LessonLeft> lessonsLeft = Lists.newArrayList();
    public List<LessonLob> lobs = Lists.newArrayList();
    public List<LessonTaken> lessonsTaken = Lists.newArrayList();
    public PpcVisit lastPpcVisit;
    @SerializedName("lastLessonNote")
    public String lastLessonNote = "";
    @Deprecated
    private String planEndDate = "";
    // end - player_info

    public Student(String studentId, String studentIdGolftec, String wuci, String firstName, String lastName, String email, String phone) {
        this.studentId = studentId;
        this.studentIdGolftec = studentIdGolftec;
        this.wuci = wuci;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phone;
    }

    public Student() { }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
        // also map to lastVisitDate
        if (Strings.isNullOrEmpty(this.planEndDate)) {
            this.planEndDateNew = null;
        } else {
            DateTime dateTime = GTUtil.parseDateTimeSafely(this.planEndDate, GTConstants.MAIN_JSON_DATETIME_FORMATTER);
            if (dateTime != null) {
                this.planEndDateNew = dateTime.toDate();
            }
        }
    }
}
