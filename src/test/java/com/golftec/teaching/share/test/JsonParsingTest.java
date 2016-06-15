package com.golftec.teaching.share.test;

import com.golftec.teaching.common.DataGenerator;
import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.LessonState;
import com.golftec.teaching.model.LessonType;
import com.golftec.teaching.model.lesson.Coach;
import com.golftec.teaching.server.networking.request.LoginRequestData;
import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.server.networking.request.SaveLessonRequestData;
import com.golftec.teaching.server.networking.response.Response;
import com.golftec.teaching.server.networking.type.GTMethod;
import com.golftec.teaching.view.EnvironmentView;
import com.golftec.teaching.view.ProVideoView;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.joda.time.DateTime;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by hoang on 5/20/15.
 */
public class JsonParsingTest {

    @Test
    public void testParseRequest() {
        Request request = new Request(GTMethod.Login.id, new LoginRequestData("rklados", "r475869k"), "TEST_MACHINE", GTUtil.uuid());
        final String json = GTUtil.toJson(request);
        System.out.println(json);
        assertNotNull(json);
        final Request parsedBack = GTUtil.fromJson(json, Request.class);
        assertNotNull(parsedBack);
        final Optional<LoginRequestData> opLoginData = GTUtil.parseDataSafely(parsedBack.data, LoginRequestData.class);
        assertTrue(opLoginData.isPresent());
        LoginRequestData loginRequestData = opLoginData.get();
        assertEquals("rklados", loginRequestData.username);
        assertEquals("r475869k", loginRequestData.password);
    }

    @Test
    public void testParseResponse() {
        final Coach coach = new Coach();
        coach.id = "C1";
        coach.name = "Hoang";
        Response request = new Response(0, "NoError", coach, GTMethod.UNKNOWN.id, GTUtil.uuid());
        final String json = GTUtil.toJson(request);
        System.out.println(json);
        assertNotNull(json);
        final Response parsedBack = GTUtil.fromJson(json, Response.class);
        assertNotNull(parsedBack);
        final Optional<Coach> opCoach = GTUtil.parseDataSafely(parsedBack.data, Coach.class);
        assertTrue(opCoach.isPresent());
        Coach coach2 = opCoach.get();
        assertEquals(coach.id, coach2.id);
        assertEquals(coach.name, coach2.name);
    }

    @Test
    public void testParseLessonsList() {
        // prepare objects/list
        List<EnvironmentView> environments = DataGenerator.randomEnvironmentsWithLessonList();

        // parse to json
        final String json = GTUtil.toJson(environments);
        // verify
        System.out.println("ToJson:\n" + json);
        assertNotNull(json);

        // parse from json back to objects/list
        Type listType = new TypeToken<ArrayList<EnvironmentView>>() {
        }.getType();
        List<EnvironmentView> parsedBack = GTUtil.fromJson(json, listType);
        System.out.println("ParsedBack:\n" + parsedBack);
        // verify
        assertNotNull(parsedBack);
        assertEquals(environments.size(), parsedBack.size());
    }

    @Test
    public void testParseProVideos() {
        // prepare objects/list
        List<ProVideoView> proVideoViews = DataGenerator.staticProVideoList();

        // parse to json
        final String json = GTUtil.toJson(proVideoViews);
        // verify
        System.out.println("ToJson:\n" + json);
        assertNotNull(json);

        // parse from json back to objects/list
        Type listType = new TypeToken<ArrayList<ProVideoView>>() {
        }.getType();
        List<ProVideoView> parsedBack = GTUtil.fromJson(json, listType);
        System.out.println("ParsedBack:\n" + parsedBack);
        // verify
        assertNotNull(parsedBack);
        assertEquals(proVideoViews.size(), parsedBack.size());
    }

    @Test
    public void test_json_saveLesson() {
        // prepare objects/list
        String lessonId = "new-lesson-001_" + DateTime.now().getMillis();
        SaveLessonRequestData requestData = new SaveLessonRequestData();
        requestData.lessonId = lessonId;
//        requestData.wuci = "700-03-3973"; // Al Wells
//        requestData.coachId = coach.id;
//        requestData.storeId = "store-id-of-hoang";
        requestData.lessonType = LessonType.Playing;
        requestData.lessonStateId = LessonState.InProcess.intValue;
        requestData.coachNote.title = "coach note title";
        requestData.coachNote.content = "coach note content";
        requestData.selectedTecCards = Lists.newArrayList("tc-01", "tc-02");
        requestData.selectedDrills = Lists.newArrayList("dr-03", "dr-04");
        requestData.telestrationIds = Lists.newArrayList("tele-05", "tele-06");
        requestData.selectedTelestrationIds = Lists.newArrayList("tele-06");
//        requestData.startedDateTime = DateTime.now().minusDays(2).toDate();
        requestData.startedDateTime = null;
//        requestData.lastStatusDateTime = DateTime.now().minusDays(1).toDate();
        requestData.lastStatusDateTime = null;
        requestData.totalVideoLength = 5000;

        // parse to json
        final String json = GTUtil.toJson(requestData);
        // verify
        System.out.println("ToJson:\n" + json);
        assertNotNull(json);

        // parse from json back to objects/list
        SaveLessonRequestData parsedBack = GTUtil.fromJson(json, SaveLessonRequestData.class);
        System.out.println("ParsedBack:\n" + parsedBack);
        // verify
        assertNotNull(parsedBack);
        assertNotNull("start-date can be null, though it should not", parsedBack.startedDateTime);
        assertNotNull("no matter what, this is not null", parsedBack.lastStatusDateTime);
    }
}
