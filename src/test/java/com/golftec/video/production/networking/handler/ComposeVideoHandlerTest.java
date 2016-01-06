package com.golftec.video.production.networking.handler;

import com.golftec.video.production.model.ComposeVideoRequestData;
import com.golftec.video.production.model.ComposeVideoResponseData;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by ThuPT on 12/29/2015.
 */
public class ComposeVideoHandlerTest extends BaseHandlerTest {

    @Test
    public void test_composeVideoEmptyObject() {
        init();
        ComposeVideoRequestData requestData = new ComposeVideoRequestData();
        ComposeVideoResponseData responseData = clientAPI.compose(requestData);
        assertEquals("Invalid request data.", responseData.message);
        clientAPI = null;
    }

    @Test
    public void test_composeVideo() throws IOException {
        init();
        ComposeVideoRequestData requestData = new ComposeVideoRequestData();
        requestData.telestrationJsonFileUrl = "http://54.197.238.74:50003/lesson-data/0791DE3C-532D-40E4-92A8-9E4F4C8451AE/telestrations/0D63004C-1853-44CB-B9FC-6EBF63292B29/0D63004C-1853-44CB-B9FC-6EBF63292B29.json";
        requestData.telestrationWavFileUrl = "http://54.197.238.74:50003/lesson-data/0791DE3C-532D-40E4-92A8-9E4F4C8451AE/telestrations/0D63004C-1853-44CB-B9FC-6EBF63292B29/0D63004C-1853-44CB-B9FC-6EBF63292B29.wav";
        requestData.telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";
        requestData.isForceCompose = true;
        ComposeVideoResponseData responseData = clientAPI.compose(requestData);
        assertEquals("The telestration is processing.", responseData.message);
        clientAPI = null;
    }


}