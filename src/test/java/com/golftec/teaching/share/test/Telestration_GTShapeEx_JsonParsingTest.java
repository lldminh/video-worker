package com.golftec.teaching.share.test;

import com.golftec.teaching.common.GTUtil;
import com.golftec.teaching.model.lesson.TelestrationVideo;
import com.golftec.teaching.server.networking.request.AddTelestrationToLessonRequestData;
import com.golftec.teaching.server.networking.request.Request;
import com.golftec.teaching.videoUtil.annotation.AnnotationEvent;
import com.golftec.teaching.videoUtil.drawingToolEx.*;
import com.golftec.teaching.videoUtil.history.AnnotationHistory;
import com.golftec.teaching.videoUtil.history.AnnotationStandByData;
import com.golftec.teaching.videoUtil.history.ToolBoardHistory;
import com.golftec.teaching.videoUtil.history.VideoHistory;
import com.golftec.teaching.videoUtil.util.*;
import com.golftec.teaching.view.MotionDataView2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by hoang on 2015-07-17.
 */
public class Telestration_GTShapeEx_JsonParsingTest {

    @Test
    public void test_parse_shapes() throws Exception {
        GTCircle circle = new GTCircle(20, 30, 100);
        String json = GTUtil.shapeToJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTCircle convertedBack = GTUtil.shapeFromJson(json, GTCircle.class);
        assertNotNull(convertedBack);
        double delta = 0.00000000001;
        assertEquals(20, convertedBack.getX(), delta);
        assertEquals(30, convertedBack.getY(), delta);
    }

    @Test
    public void test_parse_shape_line() throws Exception {
        GTLine line = new GTLine(20, 30, 100, 200);
        String json = GTUtil.shapeToJson(line);
        System.out.println(json);
        assertNotNull(json);

        GTLine convertedBack = GTUtil.shapeFromJson(json, GTLine.class);
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
        String json = GTUtil.shapeToJson(circle);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = GTUtil.shapeFromJson(json, GTCircle.class);
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
        String json = GTUtil.shapeToJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTSquare convertedBack = GTUtil.shapeFromJson(json, GTSquare.class);
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
        String json = GTUtil.shapeToJson(square);
        System.out.println(json);
        assertNotNull(json);

        GTShape convertedBack = GTUtil.shapeFromJson(json, GTSquare.class);
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

        String json = GTUtil.shapeListToJson(list);
        System.out.println(json);
        assertNotNull(json);

        List<GTCircle> convertedBack = GTUtil.shapeListFromJson(json, GTCircle.class);
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

        final Type listType = new TypeToken<ArrayList<GTCircle>>() {}.getType();
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

        String json = GTUtil.shapeListToJson(list);
        System.out.println(json);
        assertNotNull(json);

        List<GTShape> convertedBack = GTUtil.shapeListFromJson(json);
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

        List<GTShape> convertedBack = GTUtil.fromJson(json, GTUtil.shapeListType);
        assertNotNull(convertedBack);
        assertEquals(4, convertedBack.size());
        assertTrue(convertedBack.get(0) instanceof GTCircle);
        assertTrue(convertedBack.get(1) instanceof GTCircle);
        assertTrue(convertedBack.get(2) instanceof GTRectangle);
        assertTrue(convertedBack.get(3) instanceof GTRectangle);
    }

    @Test
    public void test_parse_telestration_response() throws Exception {
        File file = new File("/Users/hoang/Projects/GolfTec/server-svn/golftec-teaching-server/temp/test-data/telestration-response-ex.json");
        String json = GTUtil.inputStreamToString(new FileInputStream(file));
//        System.out.println(json);
        Request request = GTUtil.fromJson(json, Request.class);
        assertNotNull(request);

        assertEquals(4, request.methodId);
        assertEquals("DF8A2910-6E88-4A57-863F-AD524E83B3DB", request.requestId);
        assertEquals(com.google.gson.internal.LinkedTreeMap.class, request.data.getClass());

        Optional<AddTelestrationToLessonRequestData> opRequestData = GTUtil.parseDataSafely(request.data, AddTelestrationToLessonRequestData.class);
        assertTrue(opRequestData.isPresent());
        final AddTelestrationToLessonRequestData requestData = opRequestData.get();
//        System.out.println(requestData);
        assertEquals("l-11-01", requestData.lessonId);

        final TelestrationVideo telestrationData = requestData.telestrationData;
        assertNotNull(telestrationData);
        assertEquals("990742A6-9350-4325-B81C-3BECDA5C99CF", telestrationData.getTelestrationId());

        assertTrue(telestrationData.getLeftAnnotationHistory().frameMap.containsKey(80l));
        final AnnotationStandByData standByData80 = telestrationData.getLeftAnnotationHistory().frameMap.get(80l);
        assertNotNull(standByData80);
        assertEquals(1, standByData80.getGtShapeExList().size());
        assertEquals("com.golftec.teaching.videoUtil.drawingToolEx.GTLine", standByData80.getGtShapeExList().get(0).type);
    }

    @Test
    public void test_parse_telestration_data() throws Exception {
        File file = new File("/Users/hoang/Projects/GolfTec/server-svn/golftec-teaching-server/temp/test-data/telestration-object-ex.json");
        String json = GTUtil.inputStreamToString(new FileInputStream(file));
//        System.out.println(json);
        final TelestrationVideo telestrationData = GTUtil.fromJson(json, TelestrationVideo.class);
        assertNotNull(telestrationData);
        assertEquals("990742A6-9350-4325-B81C-3BECDA5C99CF", telestrationData.getTelestrationId());

        // annotation
        {
            final AnnotationHistory leftAnnotationHistory = telestrationData.getLeftAnnotationHistory();
            assertNotNull(leftAnnotationHistory);
            final Map<Double, AnnotationEvent> hashMap = leftAnnotationHistory.hashMap;
            assertNotNull(hashMap);
            final AnnotationEvent first = hashMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals(2341, first.getTimestampRounded());
            assertEquals(2341.0, first.getTimestamp(), 0.00001);
            assertEquals(MouseEventType.MOUSE_PRESS, hashMap.get(2341.0).mouseEventType);
            assertEquals(MouseEventType.MOUSE_DRAGGED, hashMap.get(2351.0).mouseEventType);
        }

        // annotation-frameMap
        {
            final AnnotationHistory leftAnnotationHistory = telestrationData.getLeftAnnotationHistory();
            assertNotNull(leftAnnotationHistory);
            final Map<Long, AnnotationStandByData> frameMap = leftAnnotationHistory.frameMap;
            assertNotNull(frameMap);
            assertEquals(213, frameMap.size());
            assertEquals(1, frameMap.get(80l).getGtShapeExList().size());
            final GTShape gtShape = frameMap.get(80l).getGtShapeExList().get(0);
            assertEquals("com.golftec.teaching.videoUtil.drawingToolEx.GTLine", gtShape.type);
            GTLine line = (GTLine) gtShape;
            assertNotNull(line);
            assertEquals(268, line.getStartX(), 0.000001);
            assertEquals(230, line.getEndX(), 0.000001);
            assertEquals(192, line.getStartY(), 0.000001);
            assertEquals(194, line.getEndY(), 0.000001);
            assertEquals(GTColor.RED, line.getColor());
        }

        // video frameMap
        {
            final VideoHistory leftVideoHistory = telestrationData.getLeftVideoHistory();
            assertNotNull(leftVideoHistory);
            final Map<Long, FrameData> videoFrameMap = leftVideoHistory.videoFrameMap;
            assertEquals(213, videoFrameMap.size());
            final FrameData frameData = videoFrameMap.get(3l);
            assertNotNull(frameData);
            assertEquals(0, frameData.srcFrame);
            assertEquals(3, frameData.desFrame);
            assertEquals("http://45.33.66.59:40003/pro-videos/3f2dbf42f79f119d9e6517f46b52b2b8/resized_320x480.mp4", frameData.sourceURL);
            assertEquals("", frameData.filePath);
            assertEquals(true, frameData.isFlip);
        }

        //NOTE: no longer used toolBoard
        {
            final ToolBoardHistory toolBoardHistory = telestrationData.getToolBoardHistory();
            assertNotNull(toolBoardHistory);
            final Map<Double, ToolBoardEvent> standByMap = toolBoardHistory.hashMap;
            final ToolBoardEvent first = standByMap.values().stream().findFirst().get();
            assertNotNull(first);
            assertEquals(ToolType.RED, standByMap.get(0.0).toolType);
            assertNull(standByMap.get(1.0).toolType);
        }
    }

    @Test
    public void test_parse_map_motion() throws Exception {
        // prepare data
        Map<Integer, List<MotionDataView2>> motionDataMap = Maps.newHashMap();
        {
            List<MotionDataView2> value1 = Lists.newArrayList();
            {
                MotionDataView2 mdv1 = new MotionDataView2();
                value1.add(mdv1);
            }
            {
                MotionDataView2 mdv2 = new MotionDataView2();
                value1.add(mdv2);
            }

            motionDataMap.put(1, value1);
        }

        // to json
        String json = GTUtil.toJson(motionDataMap);
        System.out.println(json);

        // convert back
        // get the type of the map
        Type type = new TypeToken<Map<Integer, List<MotionDataView2>>>() {}.getType();
        // do the convert
        Map<Integer, List<MotionDataView2>> convertedBack = GTUtil.fromJson(json, type);

        assertNotNull(convertedBack);
        assertEquals(1, convertedBack.size());
        assertTrue(convertedBack.containsKey(1));

        // to json again
        final String toJsonAgain = GTUtil.toJson(convertedBack);
        System.out.println(toJsonAgain);
        assertEquals("NOTE: not 100% guarantee equal as JSON Spec, but will still equal most of the time.", json, toJsonAgain);
    }
}
