package org.willingoxjin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Jin.Nie
 * @date 2022-02-15
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServerEurekaApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConfigServerEurekaApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
