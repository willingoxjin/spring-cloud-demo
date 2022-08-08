package org.willingoxjin.sentinel.demo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Jin.Nie
 * @date 2022-07-24
 */
// 开启apoll客户端注解
@EnableApolloConfig
@SpringBootApplication
public class SentinelApolloDemoApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(SentinelApolloDemoApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
