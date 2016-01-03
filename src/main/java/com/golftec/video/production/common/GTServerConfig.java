package com.golftec.video.production.common;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hoang on 2015-10-10.
 * Copy by Minh on 2015-12-18
 */
@Config.HotReload(value = 1, unit = TimeUnit.MINUTES)
@LoadPolicy(LoadType.FIRST)
@Config.Sources({
        "file:local_teaching_video_production.properties",
        "file:./golftec_teaching_video_production.properties",
})
public interface GTServerConfig extends Config {

    @Key("video-factory-number-of-threads")
    @DefaultValue("1")
    int VideoFactoryNumberOfThreads();

    @Key("telestration-data-file-host")
    @DefaultValue("http://54.197.238.74:50003")
    URL LessonDataFileHost();

    @Key("data-dir")
    @DefaultValue("temp")
    String DataDir();

    @Key("tcp-port")
    @DefaultValue("40002")
    int TcpPort();

    @Key("file-server-host")
    @DefaultValue("localhost")
    String FileServerHost();

    @Key("file-server-port")
    @DefaultValue("50003")
    int FileServerPort();
}
