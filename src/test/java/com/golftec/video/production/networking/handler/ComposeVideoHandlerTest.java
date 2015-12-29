package com.golftec.video.production.networking.handler;

import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.model.RequestData;
import com.golftec.video.production.model.ResponseData;
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
        RequestData requestData = new RequestData();
        ResponseData responseData = clientAPI.compose(requestData);
        assertEquals("TelestrationVideo is empty.", responseData.message);
        clientAPI = null;
    }

    @Test
    public void test_composeVideo() throws IOException {
        init();
        RequestData requestData = new RequestData();
        requestData.lessonId = "036B2E81-DBCC-47C9-A598-593AC04B0292";
        requestData.telestrationId = "F678BE97-BA3D-4ED0-B757-484E0C788FFE";
        ResponseData responseData = clientAPI.compose(requestData);
        assertEquals("TelestrationVideo is processing.", responseData.message);
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
        ResponseData compose(@Body() RequestData requestData);
    }
}