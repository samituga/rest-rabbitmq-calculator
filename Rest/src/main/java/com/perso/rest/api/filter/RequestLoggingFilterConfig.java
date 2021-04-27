package com.perso.rest.api.filter;

import com.perso.common.filter.LoggingFilterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class RequestLoggingFilterConfig extends LoggingFilterConfig {

    @Bean
    @Override
    public CommonsRequestLoggingFilter logFilter() {
        return super.logFilter();
    }
}