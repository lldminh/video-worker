package com.golftec.teaching.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * All thread pool creation should be managed here
 * Created by Hoang on 2015-04-30.
 */
public final class GTThreadPool {

    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private GTThreadPool() {
        // should not create instance
    }

    /**
     * Call the task in another thread of a prepared thread-pool.
     * To block and wait for the result of the task, call future.get()
     */
    public static <V> Future<V> submit(Callable<V> task) {
        return pool.submit(task);
    }
}
