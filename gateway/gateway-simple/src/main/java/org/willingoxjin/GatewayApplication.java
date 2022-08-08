package org.willingoxjin;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * @author Jin.Nie
 * @date 2022-02-18
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        // new SpringApplicationBuilder(GatewayApplication.class)
        //         .web(WebApplicationType.REACTIVE)
        //         .run(args);
    }

    // 由于排除了web依赖，不会自动装配 HttpMessageConverters，需要手动配置 HttpMessageConverters
    // @Bean
    // @ConditionalOnMissingBean
    // public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
    //     return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    // }

}
