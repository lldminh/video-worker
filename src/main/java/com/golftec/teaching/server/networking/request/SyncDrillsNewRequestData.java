package com.golftec.teaching.server.networking.request;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by hoang on 2015-07-27.
 */
public class SyncDrillsNewRequestData {

    /**
     * map id (string) -> timestamp AND timestampMetadata of existing items. Both id and timestamp are returned from server previously.
     */
    public List<DrillDeviceTimestamp> currentItems = Lists.newArrayList();
}
