package org.willingoxjin.sentinel.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@SpringBootApplication
public class SentinelAnnotationAspectjDemoApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SentinelAnnotationAspectjDemoApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

}
