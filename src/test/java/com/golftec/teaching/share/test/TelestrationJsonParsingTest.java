package com.golftec.teaching.share.test;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.model.types.MotionDataType;
import com.golftec.teaching.server.networking.request.AddTelestrationToLessonRequestData;
import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.videoUtil.annotation.AnnotationEvent;
import com.golftec.teaching.videoUtil.drawingTool.*;
import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.MotionHistory;
import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.videoUtil.motion.MotionEvent;
import com.golftec.teaching.videoUtil.util.*;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.aeonbits.owner.ConfigCache;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.golftec.teaching.share.test.GTShapeJsonTestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by hoang on 2015-06-03.
 */
public class TelestrationJsonParsingTest {

    private static GTShareTestConfig testConfig;

    @BeforeClass
    public static void setUp() {
        testConfig = ConfigCache.getOrCreate(GTShareTestConfig.class);
    }

    @AfterClass
    public static void tearDown() {
        testConfig = null;
    }

    @Test
    public void test_parse_telestration_response() throws Exception {
        final String telestrationResponse = testConfig.testDataDir() + "lesson-data\\test-data\\telestration-response.json";
        File file = new File(telestrationResponse);
        String json = GTUtil.inputStreamToString(new FileInputStream(file));
//        System.out.println(json);
        Request request = GTUtil.fromJson(json, Request.class);
        assertNotNull(request);

        assertEquals(4, request.methodId);
        assertEquals("9B1FE114-E98F-4CAF-B571-D8861B486E1B", request.requestId);
        assertEquals(com.google.gson.internal.LinkedTreeMap.class, request.data.getClass());

        Optional<AddTelestrationToLessonRequestData> opRequestData = GTUtil.parseDataSafely(request.data, AddTelestrationToLessonRequestData.class);
        assertTrue(opRequestData.isPresent());
        final AddTelestrationToLessonRequestData requestData = opRequestData.get();
        System.out.println(requestData);
        assertEquals("l-01-05", requestData.lessonId);
        assertNotNull(requestData.telestrationData);
        assertEquals("468369B1-9EE5-4BB0-8713-5FDB0D63FF2E", requestData.telestrationData.getTelestrationId());
    }

    @Test
    public void test_parse_telestration_response2() throws Exception {
        final String telestrationResponse = testConfig.testDataDir() + "lesson-data\\test-data\\telestration-response-2.json";
        File file = new File(telestrationResponse);
        String json = GTUtil.inputStreamToString(new FileInputStream(file));
//        System.out.println(json);
        Request request = GTUtil.fromJson(json, Request.class);
        assertNotNull(request);

        assertEquals(4, request.methodId);
        assertEquals("2B3944F5-B6EC-49A7-9D19-94C3258280AB", request.requestId);
        assertEquals("0E32F86C-D04C-42FB-B693-CBDE9899D023", request.installationId);
        assertEquals(com.google.gson.internal.LinkedTreeMap.class, request.data.getClass());

        Optional<AddTelestrationToLessonRequestData> opRequestData = GTUtil.parseDataSafely(request.data, AddTelestrationToLessonRequestData.class);
        assertTrue(opRequestData.isPresent());
        final AddTelestrationToLessonRequestData requestData = opRequestData.get();
//        final String convertBackToJson = GTUtil.toJson(requestData);
//        System.out.println(convertBackToJson);
//        String jsonData = GTUtil.readFileToString(new File("/Users/hoang/Projects/GolfTec/server-svn/golftec-teaching-server/temp/test-data/telestration-object-2.json"));
//        assertEquals(jsonData, convertBackToJson);

        assertEquals("l-04-01", requestData.lessonId);
        assertNotNull(requestData.telestrationData);
        assertEquals("29712522-5EA1-474C-A1C9-150DA1CBB76B", requestData.telestrationData.getTelestrationId());

        assertEquals(1, requestData.telestrationData.getLeftAnnotationHistory().standByMap.size());
        //TODO: `list` is red so I need to comment it
//        assertEquals(2, requestData.telestrationData.getLeftAnnotationHistory().standByMap.get(0d).list.size());
//        assertEquals(UUID.fromString("39ab3e38-afdd-4c18-bce0-aa3976acf311"), requestData.telestrationData.getLeftAnnotationHistory().standByMap.get(0d).list.get(0).id);
//        assertEquals(UUID.fromString("f5639534-1cd3-4d59-b1c7-e7064b868784"), requestData.telestrationData.getLeftAnnotationHistory().standByMap.get(0d).list.get(1).id);
    }

    @Test
    public void test_parse_telestration_data() throws Exception {
        final String telestrationObject = testConfig.testDataDir() + "lesson-data\\test-data\\telestration-object.json";
        File file = new File(telestrationObject);
        String json = GTUtil.inputStreamToString(new FileInputStream(file));
//        System.out.println(json);
        final TelestrationVideo telestrationData = GTUtil.fromJson(json, TelestrationVideo.class);
        assertNotNull(telestrationData);
        assertEquals("468369B1-9EE5-4BB0-8713-5FDB0D63FF2E", telestrationData.getTelestrationId());

        // annotation
        {
            final AnnotationHistory leftAnnotationHistory = telestrationData.getLeftAnnotationHistory();
            assertNotNull(leftAnnotationHistory);
            final Map<Double, AnnotationEvent> hashMap = leftAnnotationHistory.hashMap;
            assertNotNull(hashMap);
            final AnnotationEvent first = hashMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals(9658, first.getTimestampRounded());
            assertEquals(9658.208013, first.getTimestamp(), 0.00001);
            assertEquals(MouseEventType.MOUSE_PRESS, hashMap.get(9658.208013).mouseEventType);
            assertEquals(MouseEventType.MOUSE_RELEASED, hashMap.get(9857.811987).mouseEventType);
            assertEquals(MouseEventType.MOUSE_DRAGGED, hashMap.get(9771.820962).mouseEventType);
        }

        // video
        {
            final VideoHistory leftVideoHistory = telestrationData.getLeftVideoHistory();
            assertNotNull(leftVideoHistory);
            final Map<Double, VideoBoardEvent> hashMap = leftVideoHistory.hashMap;
            final VideoBoardEvent first = hashMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals(VideoButtonType.SET_PATH, hashMap.get(3641.360998).videoButtonType);
            assertEquals(VideoButtonType.PLAY, hashMap.get(8510.528982).videoButtonType);
        }

        // toolBoard
        {
            final ToolBoardHistory toolBoardHistory = telestrationData.getToolBoardHistory();
            assertNotNull(toolBoardHistory);
            final Map<Double, ToolBoardEvent> standByMap = toolBoardHistory.standByMap;
            final ToolBoardEvent first = standByMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals(ToolType.RED, standByMap.get(0.0).toolType);
            assertEquals(ToolType.LINE, standByMap.get(1.0).toolType);
        }

        // motion
        {
            final MotionHistory rightMotionHistory = telestrationData.getRightMotionHistory();
            assertNotNull(rightMotionHistory);
            final Map<Double, MotionEvent> hashMap = rightMotionHistory.hashMap;
            final MotionEvent first = hashMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals("add", hashMap.get(12757.60597).method);
            assertEquals(MotionDataType.SHTLT, hashMap.get(12757.60597).motionDataType);
            assertEquals("move", hashMap.get(12810.23097).method);
            assertEquals(MotionDataType.SHTLT, hashMap.get(12810.23097).motionDataType);
        }
    }

    @Test
    public void test_parse_shapes() throws Exception {
        GTCircle circle = new GTCircle(20, 30, 100);
        String json = shapeToJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTCircle convertedBack = shapeFromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        double delta = 0.00000000001;
        assertEquals(20, convertedBack.getX(), delta);
        assertEquals(30, convertedBack.getY(), delta);
    }

    @Test
    public void test_parse_shape_line() throws Exception {
        GTLine line = new GTLine(20, 30, 100, 200);
        String json = shapeToJson(line);
        System.out.println(json);
        assertNotNull(json);

        GTLine convertedBack = shapeFromJson(json, GTLine.class);
        assertNotNull(convertedBack);
    }

    @Test
    public void test_parse_shapes_hierarchy() throws Exception {
        GTCircle circle = new GTCircle(20, 30, 100);
        String json = GTUtil.toJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTCircle convertedBack = GTUtil.fromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        double delta = 0.00000000001;
        assertEquals(20, convertedBack.getX(), delta);
        assertEquals(30, convertedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_indirect() throws Exception {
        GTShape circle = new GTCircle(20, 30, 100);
        String json = shapeToJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = shapeFromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        GTCircle castedBack = (GTCircle) convertedBack;
        double delta = 0.00000000001;
        assertEquals(20, castedBack.getX(), delta);
        assertEquals(30, castedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_indirect_hierarchy() throws Exception {
        GTShape circle = new GTCircle(20, 30, 100);
        String json = GTUtil.toJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = GTUtil.fromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        GTCircle castedBack = (GTCircle) convertedBack;
        double delta = 0.00000000001;
        assertEquals(20, castedBack.getX(), delta);
        assertEquals(30, castedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_grand_children() throws Exception {
        GTSquare square = new GTSquare(20, 30, 100);
        String json = shapeToJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTSquare convertedBack = shapeFromJson(json, GTSquare.class);
        assertNotNull(convertedBack);
        double delta = 0.00000000001;
        assertEquals(20, convertedBack.getX(), delta);
        assertEquals(30, convertedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_grand_children_hierarchy() throws Exception {
        GTSquare square = new GTSquare(20, 30, 100);
        String json = GTUtil.toJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTSquare convertedBack = GTUtil.fromJson(json, GTSquare.class);
        assertNotNull(convertedBack);
        double delta = 0.00000000001;
        assertEquals(20, convertedBack.getX(), delta);
        assertEquals(30, convertedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_grand_children_indirect() throws Exception {
        GTShape square = new GTSquare(20, 30, 100);
        String json = shapeToJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = shapeFromJson(json, GTSquare.class);
        assertNotNull(convertedBack);
        GTSquare castedBack = (GTSquare) convertedBack;
        double delta = 0.00000000001;
        assertEquals(20, castedBack.getX(), delta);
        assertEquals(30, castedBack.getY(), delta);
    }

    @Test
    public void test_parse_shapes_grand_children_indirect_hierarchy() throws Exception {
        GTShape square = new GTSquare(20, 30, 100);
        String json = GTUtil.toJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = GTUtil.fromJson(json, GTSquare.class);
        assertNotNull(convertedBack);
        GTSquare castedBack = (GTSquare) convertedBack;
        double delta = 0.00000000001;
        assertEquals(20, castedBack.getX(), delta);
        assertEquals(30, castedBack.getY(), delta);
    }

    @Test
    public void test_parse_shape_list() throws Exception {
        List<GTCircle> list = Lists.newArrayList();
        final GTCircle c1 = new GTCircle(11, 12, 13);
        list.add(c1);
        final GTCircle c2 = new GTCircle(21, 22, 23);
        list.add(c2);

        String json = shapeListToJson(list);
        System.out.println(json);
        assertNotNull(json);

        List<GTCircle> convertedBack = shapeListFromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        assertEquals(2, convertedBack.size());
    }

    @Test
    public void test_parse_shape_list_hierarchy() throws Exception {
        List<GTCircle> list = Lists.newArrayList();
        final GTCircle c1 = new GTCircle(11, 12, 13);
        list.add(c1);
        final GTCircle c2 = new GTCircle(21, 22, 23);
        list.add(c2);

        String json = GTUtil.toJson(list);
        System.out.println(json);
        assertNotNull(json);

        final Type listType = new TypeToken<ArrayList<GTCircle>>() {
        }.getType();
        List<GTCircle> convertedBack = GTUtil.fromJson(json, listType);
        assertNotNull(convertedBack);
        assertEquals(2, convertedBack.size());
    }

    @Test
    public void test_parse_shape_list_indirect() throws Exception {
        List<GTShape> list = Lists.newArrayList();
        final GTCircle c1 = new GTCircle(11, 12, 13);
        list.add(c1);
        final GTShape c2 = new GTCircle(21, 22, 23);
        list.add(c2);

        final GTRectangle r1 = new GTRectangle(31, 32, 33, 34);
        list.add(r1);

        final GTShape r2 = new GTRectangle(41, 42, 43, 44);
        list.add(r2);

        String json = shapeListToJson(list);
        System.out.println(json);
        assertNotNull(json);

        List<GTShape> convertedBack = shapeListFromJson(json);
        assertNotNull(convertedBack);
        assertEquals(4, convertedBack.size());
        assertTrue(convertedBack.get(0) instanceof GTCircle);
        assertTrue(convertedBack.get(1) instanceof GTCircle);
        assertTrue(convertedBack.get(2) instanceof GTRectangle);
        assertTrue(convertedBack.get(3) instanceof GTRectangle);
    }

    @Test
    public void test_parse_shape_list_indirect_hierarchy() throws Exception {
        List<GTShape> list = Lists.newArrayList();
        final GTCircle c1 = new GTCircle(11, 12, 13);
        list.add(c1);
        final GTShape c2 = new GTCircle(21, 22, 23);
        list.add(c2);

        final GTRectangle r1 = new GTRectangle(31, 32, 33, 34);
        list.add(r1);

        final GTShape r2 = new GTRectangle(41, 42, 43, 44);
        list.add(r2);

        String json = GTUtil.toJson(list);
        System.out.println(json);
        assertNotNull(json);

        List<GTShape> convertedBack = GTUtil.fromJson(json, shapeListType);
        assertNotNull(convertedBack);
        assertEquals(4, convertedBack.size());
        assertTrue(convertedBack.get(0) instanceof GTCircle);
        assertTrue(convertedBack.get(1) instanceof GTCircle);
        assertTrue(convertedBack.get(2) instanceof GTRectangle);
        assertTrue(convertedBack.get(3) instanceof GTRectangle);
    }
}
