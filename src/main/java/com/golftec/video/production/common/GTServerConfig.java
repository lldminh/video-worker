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
@LoadPolicy(LoadType.FIRST)
@Config.Sources({
        "file:local_teaching_video_production.properties",
        "file:./golftec_teaching_video_production.properties",
})
public interface GTServerConfig extends Config {

    @Key("aws-key-id")
    @DefaultValue("AKIAIV5N2DEWS4KR75PA")
    String AWSAccessKeyId();

    @Key("aws-secret-key")
    @DefaultValue("aj+bBW842jkU6x7RcVqFwRld+lcBiH0EPhc2OOLD")
    String AWSSecretKey();

    @Key("database-url")
    @DefaultValue("jdbc:mysql://localhost:3306/golftec3")
    String DatabaseUrl();

    @Key("database-user")
    @DefaultValue("root")
    String DatabaseUser();

    @Key("database-password")
    @DefaultValue("111111")
    String DatabasePassword();

    @Key("tcp-port")
    @DefaultValue("40002")
    int TcpPort();

    @Key("http-port")
    @DefaultValue("40003")
    int HttpPort();

    @Key("http-port-report")
    @DefaultValue("40004")
    int HttpPortReport();

    @Key("can-refresh-cache")
    @DefaultValue("false")
    boolean CanRefreshCache();

    @Key("server-address")
    @DefaultValue("localhost")
    String ServerAddress();

    @Key("test-mode")
    @DefaultValue("false")
    boolean TestMode();

    @Key("max-drills")
    @DefaultValue("2000")
    long MaxDrills();

    @Key("max-pro-videos")
    @DefaultValue("2000")
    long MaxProVideos();

    @Key("max-tec-cards")
    @DefaultValue("100")
    long MaxTecCards();

    @Key("max-student-search-results")
    @DefaultValue("20")
    long MaxStudentSearchResults();

    @Key("data-dir")
    @DefaultValue("temp")
    String DataDir();

    @Key("max-post-process-count")
    @DefaultValue("3")
    int MaxPostProcessCount();

    @Key("max-telestration-process-count")
    @DefaultValue("200000")
    int MaxTelestrationProcessCount();

    @Key("file-size-diff-threshold")
    @DefaultValue("10")
    long FileSizeDiffThreshold();

    @Key("process-telestrations-on-startup")
    @DefaultValue("false")
    boolean ProcessTelestrationsOnStartup();

    @Key("video-factory-number-of-threads")
    @DefaultValue("2")
    int VideoFactoryNumberOfThreads();

    @Key("aws-dynamodb-endpoint")
    @DefaultValue("http://dynamodb.us-east-1.amazonaws.com")
    String AWSDynamoDBEndpoint();

    @Key("aws-dynamodb-lesson-table-name")
    @DefaultValue("local_thu_lesson")
    String AWSDynamoDBLesson();

    @Key("aws-dynamodb-telestration-table-name")
    @DefaultValue("sandbox_telestration")
    String AWSDynamoDBTeletration();

    @Key("aws-dynamodb-read-throughput")
    @DefaultValue("1")
    long AWSDynamoDBReadThroughput();

    @Key("aws-dynamodb-write-throughput")
    @DefaultValue("1")
    long AWSDynamoDBWriteThroughput();

}
