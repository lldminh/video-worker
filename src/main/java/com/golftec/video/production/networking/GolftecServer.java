package com.golftec.video.production.networking;


import com.golftec.video.production.common.MPJsonUtils;
import com.golftec.video.production.networking.handler.ComposeVideoHandler;
import com.golftec.video.production.networking.handler.GetTelestrationStatusHandler;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.util.concurrent.Executors;

import static spark.Spark.*;

public final class GolftecServer {

    private static final Logger log = LoggerFactory.getLogger(GolftecServer.class);

    private final int port;

    public GolftecServer(int port) {
        this.port = port;
    }

    public ListenableFuture<?> run() {
        return MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor()).submit(() -> {
            try {
                port(port);


                externalStaticFileLocation(".");

                before((req, res) -> {
                    log.info("{} ==> {} {} {}", req.ip(), req.requestMethod(), req.pathInfo(), req.body());
                    res.type("application/json; charset=UTF-8");
                    res.header("Server", "Golftec teaching video production");
                    res.header("Access-Control-Allow-Origin", "*");
                });

                after((req, res) -> log.info("{} <== {} {}", req.ip(), req.requestMethod(), req.pathInfo()));

                options("/*", (req, res) -> {
                    addCorsHeaders(req, res);
                    return "";
                });

                // compose telestration video method
                post("/golftec/map/compose-video", new ComposeVideoHandler()::handle, MPJsonUtils::toJson);
                //get telestration status
                post("/golftec/map/get-telestration-status", new GetTelestrationStatusHandler()::handle, MPJsonUtils::toJson);
                log.info("GolftecServer started, listening on port: {}", port);
            } catch (Exception e) {
                log.error("GolftecServer.run: Should not be here, the exception() clause above should be enough.", e);
            }
        });
    }

    private void addCorsHeaders(Request req, Response res) {

        String allowedMethods = req.headers("Access-Control-Request-Method");
        res.header("Access-Control-Allow-Methods", allowedMethods);

        String allowedHeaders = req.headers("Access-Control-Request-Headers");
        res.header("Access-Control-Allow-Headers", allowedHeaders);
    }

    public void stopServer() {
        stop();
    }
}
