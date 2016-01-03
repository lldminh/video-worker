package com.golftec.video.production.common;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                .setHandler(new HttpHandler() {
                    @Override
                    public void handleRequest(final HttpServerExchange exchange) throws Exception {
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World");
                    }
                }).build();
    }

    public Undertow get() {
        return server;
    }
}
