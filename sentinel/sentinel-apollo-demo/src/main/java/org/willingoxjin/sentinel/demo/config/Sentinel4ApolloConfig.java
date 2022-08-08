package org.willingoxjin.sentinel.demo.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin.Nie
 * @date 2022-07-24
 */
@Configuration
public class Sentinel4ApolloConfig {

    @Bean
    @ConditionalOnMissingBean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApolloDataSourceListener apolloDataSourceListener(
            @Value("${spring.application.name}") String applicationName) {
        return new ApolloDataSourceListener(applicationName);
    }

}
