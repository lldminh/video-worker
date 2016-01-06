package com.golftec.video.production.networking.handler;

import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.model.ComposeVideoRequestData;
import com.golftec.video.production.model.ComposeVideoResponseData;
import com.golftec.video.production.model.DeleteTelestrationOutputRequestData;
import com.golftec.video.production.model.DeleteTelestrationOutputResponseData;
import com.squareup.okhttp.OkHttpClient;
import junit.framework.TestCase;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by ThuPT on 1/6/2016.
 */
public class BaseHandlerTest extends TestCase {
    GTClientAPI clientAPI;

    public void init() {
        OkHttpClient okClient = new OkHttpClient();
        this.clientAPI = new RestAdapter.Builder()
                .setEndpoint("http://localhost:50006")
                .setClient(new OkClient(okClient))
                .setConverter(new GsonConverter(MPJsonUtils.gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(GTClientAPI.class);
    }


    interface GTClientAPI {
        @POST("/golftec/map/compose-video")
        ComposeVideoResponseData compose(@Body() ComposeVideoRequestData requestData);

        @POST("/golftec/map/delete-telestration-output")
        DeleteTelestrationOutputResponseData deleteTelestrationOutput(@Body() DeleteTelestrationOutputRequestData requestData);
    }
}
