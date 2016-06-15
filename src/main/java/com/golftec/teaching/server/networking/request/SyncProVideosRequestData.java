package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by hoang on 2015-07-23.
 */
public class SyncProVideosRequestData {

    /**
     * map id (string) -> timestamp (long) of existing items. Both id and timestamp are returned from server previously.
     */
    public Map<String, Long> currentItems = Maps.newHashMap();
}
