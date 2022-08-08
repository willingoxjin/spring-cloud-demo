package org.willingoxjin.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Jin.Nie
 * @date 2022-07-31
 */
@EnableDubbo
@SpringBootApplication
public class DubboServiceApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboServiceApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
