package com.golftec.video.production.networking.handler;

import com.golftec.video.production.data.GTResponseCode;
import com.golftec.video.production.model.*;
import org.junit.Test;

/**
 * Created by ThuPT on 1/6/2016.
 */
public class DeleteTelestrationOutputHandlerTest extends BaseHandlerTest {

    //invalid request data
    @Test
    public void test_emptyRequest() {
        init();
        DeleteTelestrationOutputRequestData requestData = new DeleteTelestrationOutputRequestData();
        DeleteTelestrationOutputResponseData responseData = clientAPI.deleteTelestrationOutput(requestData);
        assertEquals(GTResponseCode.NotOk.id, responseData.code);
        assertEquals("Invalid request data.", responseData.errorMessage);
        clientAPI = null;
    }

    //delete non-exist telestration
    @Test
    public void test_deleteNonExistTelestration() {
        init();
        DeleteTelestrationOutputRequestData requestData = new DeleteTelestrationOutputRequestData();
        requestData.telestrationId = "not-existed-telestraion";
        DeleteTelestrationOutputResponseData responseData = clientAPI.deleteTelestrationOutput(requestData);
        assertEquals(GTResponseCode.NotOk.id, responseData.code);
        assertEquals("Telestration is not composed.", responseData.errorMessage);
        clientAPI = null;
    }

    //delete composing telestration
    @Test
    public void test_deleteComposingTelestration() {
        init();
        ComposeVideoRequestData requestData = new ComposeVideoRequestData();
        requestData.telestrationJsonFileUrl = "http://54.197.238.74:50003/lesson-data/0791DE3C-532D-40E4-92A8-9E4F4C8451AE/telestrations/0D63004C-1853-44CB-B9FC-6EBF63292B29/0D63004C-1853-44CB-B9FC-6EBF63292B29.json";
        requestData.telestrationWavFileUrl = "http://54.197.238.74:50003/lesson-data/0791DE3C-532D-40E4-92A8-9E4F4C8451AE/telestrations/0D63004C-1853-44CB-B9FC-6EBF63292B29/0D63004C-1853-44CB-B9FC-6EBF63292B29.wav";
        requestData.telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";
        requestData.isForceCompose = true;
        clientAPI.compose(requestData);

        DeleteTelestrationOutputRequestData requestDeleteData = new DeleteTelestrationOutputRequestData();
        requestDeleteData.telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";
        DeleteTelestrationOutputResponseData responseDeleteData = clientAPI.deleteTelestrationOutput(requestDeleteData);
        assertEquals(GTResponseCode.NotOk.id, responseDeleteData.code);
        assertEquals("Telestration is not composed.", responseDeleteData.errorMessage);
        clientAPI = null;
    }

    //delete composed telestration
    @Test
    public void test_deleteComposedTelestration() {
        init();

        DeleteTelestrationOutputRequestData requestDeleteData = new DeleteTelestrationOutputRequestData();
        requestDeleteData.telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";
        DeleteTelestrationOutputResponseData responseDeleteData = clientAPI.deleteTelestrationOutput(requestDeleteData);
        assertEquals(GTResponseCode.Ok.id, responseDeleteData.code);
        assertEquals("", responseDeleteData.errorMessage);
        clientAPI = null;
    }

    @Test
    public void test_getTelestration() {
        init();

        GetTelestrationStatusRequestData resquestData = new GetTelestrationStatusRequestData();
        resquestData.telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";

        GetTelestrationStatusResponseData responseData = clientAPI.getTelestationStatus(resquestData);
        assertNotNull(responseData);
        clientAPI = null;
    }

    @Test
    public void test_getStatusWorker() {
        init();

        StatusWorkerResponseData responseData = clientAPI.getWorkerStatus("");
        assertEquals(GTResponseCode.NotOk.id, responseData.code);

        clientAPI = null;
    }

}
