package org.willingoxjin.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin.Nie
 * @date 2021-12-28
 */
@Configuration
// @RibbonClient(value = "eureka-client", configuration = com.netflix.loadbalancer.RandomRule.class)
public class RibbonConfiguration {

    // @Bean
    // public IRule defaultLBStrategy() {
    //     // RandomRule-随机策略
    //     return new RandomRule();
    // }

}