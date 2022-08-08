package org.willingoxjin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Jin.Nie
 * @date 2022-04-11
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosProviderApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
