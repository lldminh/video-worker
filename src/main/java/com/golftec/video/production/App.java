package com.golftec.video.production;

import com.golftec.teaching.common.GTUtil;
import com.golftec.video.production.common.GTServerConfig;
import com.golftec.video.production.common.GTServerConstant;
import com.golftec.video.production.common.GTServerFile;
import com.golftec.video.production.common.GTServerUtil;
import com.golftec.video.production.networking.GolftecServer;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import org.aeonbits.owner.ConfigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.util.concurrent.ExecutionException;

/**
 * Created by Hoang on 2015-04-08.
 */
public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        GTUtil.updateTimezoneToUtc();

        log.info("GolfTec Server Starting. v{}", GTServerConstant.VERSION);
        log.info("ManagementFactory.getRuntimeMXBean().getName(): {}", ManagementFactory.getRuntimeMXBean().getName());
        log.debug("System.getProperty(\"java.class.path\"): {}", System.getProperty("java.class.path"));

        //Set values get from file .properties into ConfigOption
        final GTServerConfig config = ConfigCache.getOrCreate(GTServerConfig.class);
        log.info("Configuration parsed: {}", config);
        GTServerConstant.ConfigOption = config;

        GTServerUtil.initAfterOptionParsed();
        GTServerUtil.initTelestrationStatus();

        // start golftec socket server
        new GolftecServer(GTServerConstant.ConfigOption.TcpPort()).run().get();

        //start file server
        new GTServerFile(GTServerConstant.ConfigOption.FileServerHost(), GTServerConstant.ConfigOption.FileServerPort()).get().start();
        log.info("GolfTec Servers Started");
    }
}
