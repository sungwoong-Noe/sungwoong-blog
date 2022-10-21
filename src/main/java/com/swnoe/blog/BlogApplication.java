package com.swnoe.blog;

import com.swnoe.blog.config.aop.LogTraceAspectConfig;
import com.swnoe.blog.logtrace.trace.LogTrace;
import com.swnoe.blog.logtrace.trace.ThreadLocalLogTrace;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EntityScan("com.swnoe.blog.domain")
@EnableJpaAuditing
@Import(LogTraceAspectConfig.class)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }

}
