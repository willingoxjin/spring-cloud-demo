package org.willingojin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * @author Jin.Nie
 * @date 2022-02-09
 */
@EnableDiscoveryClient
// @EnableHystrix
// @EnableCircuitBreaker // @EnableHystrix 中包含了 @EnableCircuitBreaker，可以不加
@EnableTurbine
@EnableAutoConfiguration
public class TurbineApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TurbineApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
