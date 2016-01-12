package com.golftec.video.production.common;

import io.undertow.Undertow;
import io.undertow.server.handlers.resource.PathResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

import static io.undertow.Handlers.resource;

/**
 * Created by ThuPT on 1/3/2016.
 */
public class GTServerFile {

    private static final Logger log = LoggerFactory.getLogger(GTServerFile.class);

    Undertow server;

    public GTServerFile(String host, int port) {
        log.info("init GTServerFile");
        server = Undertow.builder()
                .addHttpListener(port, host)
                .setHandler(resource(new PathResourceManager(
                        Paths.get(GTServerConstant.ConfigOption.DataDir())
                        , GTServerConstant.ConfigOption.TransferMinSize()))
                        .setDirectoryListingEnabled(true)).build();
        log.info("GTServerFile started, listening on port: {}", port);
    }

    public Undertow get() {
        return server;
    }
}
