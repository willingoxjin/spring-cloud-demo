package org.willingoxjin.sentinel.demo.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfigurer
 * WebMvcConfigurationSupport
 * @author Jin.Nie
 * @date 2022-07-15
 */
@Configuration
public class InterceptorConfig {

    @Bean
    public SentinelWebInterceptor sentinelWebInterceptor() {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();
        // Enable the HTTP method prefix.
        // config.setHttpMethodSpecify(true);
        config.setWebContextUnify(true);
        return new SentinelWebInterceptor(config);
    }

}