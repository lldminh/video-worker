package com.golftec.video.production.service;

import com.golftec.video.production.common.GTServerConfig;
import com.golftec.video.production.common.GTServerConstant;
import com.golftec.video.production.common.GTServerUtil;
import org.aeonbits.owner.ConfigCache;
import org.junit.Test;

/**
 * Created by ThuPT on 12/30/2015.
 */
public class VideoServiceTest {

    @Test
    public void test_composeVideo() {
        final GTServerConfig config = ConfigCache.getOrCreate(GTServerConfig.class);
        GTServerConstant.ConfigOption = config;
        GTServerUtil.initAfterOptionParsed();
        String lessonId = "0791DE3C-532D-40E4-92A8-9E4F4C8451AE";
        String telestrationId = "0D63004C-1853-44CB-B9FC-6EBF63292B29";
        VideoService videoService = new VideoService();
        videoService.composeTelestrationVideo(lessonId, telestrationId);
    }
}