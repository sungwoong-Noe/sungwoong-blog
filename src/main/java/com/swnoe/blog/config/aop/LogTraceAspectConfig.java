package com.swnoe.blog.config.aop;

import com.swnoe.blog.logtrace.aspect.LogTraceAspect;
import com.swnoe.blog.logtrace.trace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceAspectConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }
}
