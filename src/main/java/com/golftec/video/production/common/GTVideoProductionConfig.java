package com.golftec.video.production.common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

import java.util.concurrent.TimeUnit;

/**
 * Created by Hoang on 2015-10-10.
 * Copy by Minh on 2015-12-18
 */
@Config.HotReload(value = 1, unit = TimeUnit.MINUTES)
@LoadPolicy(LoadType.MERGE)
@Config.Sources({
        "file:golftec-teaching-video-production-local.properties",
        "file:./golftec-teaching-video-production.properties",
})
public interface GTVideoProductionConfig extends Config {

    @Key("video-factory-number-of-threads")
    @DefaultValue("1")
    int VideoFactoryNumberOfThreads();

    @Key("data-dir")
    @DefaultValue("temp")
    String DataDir();

    @Key("tcp-port")
    @DefaultValue("50006")
    int TcpPort();

    @Key("worker-file-server-host")
    @DefaultValue("localhost")
    String workerFileServerHost();

    @Key("worker-file-server-port")
    @DefaultValue("50007")
    int workerFileServerPort();

    @Key("transfer-min-size")
    @DefaultValue("100")
    int TransferMinSize();
}
