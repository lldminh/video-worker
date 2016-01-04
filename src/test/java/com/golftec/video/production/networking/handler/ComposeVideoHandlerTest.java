package com.golftec.video.production.networking.handler;

import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.model.ComposeVideoRequestData;
import com.golftec.video.production.model.ComposeVideoResponseData;
import com.squareup.okhttp.OkHttpClient;
import junit.framework.TestCase;
import org.junit.Test;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.POST;

import java.io.IOException;

/**
 * Created by ThuPT on 12/29/2015.
 */
public class ComposeVideoHandlerTest extends TestCase {
    GTClientAPI clientAPI;

    @Test
    public void test_composeVideoEmptyObject() {
        init();
        ComposeVideoRequestData requestData = new ComposeVideoRequestData();
        ComposeVideoResponseData responseData = clientAPI.compose(requestData);
        assertEquals("TelestrationVideo is empty.", responseData.message);
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


    public void init() {
        OkHttpClient okClient = new OkHttpClient();
        this.clientAPI = new RestAdapter.Builder()
                .setEndpoint("http://localhost:40002")
                .setClient(new OkClient(okClient))
                .setConverter(new GsonConverter(MPJsonUtils.gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(GTClientAPI.class);
    }


    interface GTClientAPI {
        @POST("/golftec/map/compose-video")
        ComposeVideoResponseData compose(@Body() ComposeVideoRequestData requestData);
    }
}