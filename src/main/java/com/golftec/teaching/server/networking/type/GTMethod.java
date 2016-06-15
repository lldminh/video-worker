package com.golftec.teaching.server.networking.type;

import com.google.common.collect.Maps;

import java.util.Map;

public enum GTMethod {
    UNKNOWN(-1),
    Login(1),
    @Deprecated GetLessonsByCoach(2),
    ReserveLesson(3),
    AddTelestrationToLesson(4),
    FinishLesson(5),
    ReleaseLesson(6),
    SelectLesson(7),
    AbandonLesson(8),
    GetStudents(9),
    AddStudent(10),
    GetSchedules(11),
    StartLesson(12),
    AddLesson(13),
    AddSwingToLesson(14),
    UpdateSwing(15),
    UpdateLessonCoachNote(16),
    UpdateLessonDrills(17),
    GetLessonTypes(18),
    StartNewLessonWithNewStudent(19),
    StartLessonFromSchedule(20),
    UpdateLessonTecCards(21),
    @Deprecated GetLessonsByCoach2(22),
    @Deprecated GetLessonTecCards(23),
    UpdateCoachProVideoPreferences(24),
    SyncProVideos(25),
    SyncDrills(26),
    SyncTecCards(27),
    GetCoachProVideoPreferences(28),
    GetLessonById(29),
    SearchStudent(30),
    GetSchedulesFlat(31),
    GetActiveLessonsByCoach(32),
    CheckFileValidity(33),
    SaveLesson(34),
    SaveTelestrationMeta(35),
    AddDrawingToLesson(36),
    CheckUpdateVersion(37),
    SyncDrills_Type2(94),
    GetListCategoryDrill(95),
    ConfirmDeleteLesson(96),
    GetDrillsByCoach(97),
    GetSwingTypes(98),
    GetProVideosByCoach(99),
    DeleteTelestrationById(100),
    RefreshCache(1669878137),;

    private static final Map<Integer, GTMethod> itemMap = Maps.newHashMap();

    static {
        for (GTMethod method : GTMethod.values()) {
            itemMap.put(method.id, method);
        }
    }

    public int id;

    GTMethod(int id) {
        this.id = id;
    }

    public static GTMethod get(int id) {
        return itemMap.getOrDefault(id, UNKNOWN);
    }
}
