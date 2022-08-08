package org.willingoxjin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Jin.Nie
 * @date 2022-04-12
 */
@SpringBootApplication
public class NacosConfigApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(NacosConfigApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
