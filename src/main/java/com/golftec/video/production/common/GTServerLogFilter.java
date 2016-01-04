package com.golftec.video.production.common;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * Created by Hoang on 2014-08-09.
 */
public class GTServerLogFilter extends Filter<ILoggingEvent> {

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLoggerName().contains("com.j256.ormlite")) {
            return FilterReply.DENY;
        }
        if (event.getLoggerName().contains("jdbc.JdbcConnectionSource")) {
            return FilterReply.DENY;
        }
        if (event.getLoggerName().contains("jdbc.JdbcPooledConnectionSource")) {
            return FilterReply.DENY;
        }
        if (event.getLoggerName().contains("org.quartz")) {
            return FilterReply.DENY;
        }
        if (event.getLoggerName().contains("org.eclipse.jetty")) {
            return FilterReply.DENY;
        }
        if (event.getLoggerName().contains(".JettySparkServer")) {
            return FilterReply.DENY;
        }

        return FilterReply.NEUTRAL;
    }
}
