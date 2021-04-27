package com.perso.common.filter;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public abstract class LoggingFilterConfig {

    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setIncludeHeaders(true);
        filter.setMaxPayloadLength(10000);
        filter.setAfterMessagePrefix("Request data : ");
        return filter;
    }
}