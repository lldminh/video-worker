package com.golftec.teaching.share.test;

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
        "file:golftec-teaching-server-test.properties",
        "file:golftec-teaching-server-local.properties",
        "file:./golftec-teaching-server.properties",
})
public interface GTShareTestConfig extends Config {

    @Key("test-data-dir")
    @DefaultValue("C:\\\\Work\\\\MonsterPixel\\\\GolfTec\\\\source\\\\server\\\\golftec-teaching-server\\\\temp\\\\")
    String testDataDir();
}
