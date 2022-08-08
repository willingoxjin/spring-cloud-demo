package org.willingoxjin.sleuth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jin.Nie
 * @date 2022-07-06
 */
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class SleuthTraceOneApp {

    // RestTemplate --------------------------------------------------------------

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // controller ----------------------------------------------------------------

    @RestController
    public static class TraceController {
        @Autowired
        private RestTemplate restTemplate;

        @GetMapping(value = "/traceOne")
        public String traceA() {
            log.info("-------Trace One");
            // 调用 traceTwo
            return restTemplate.getForEntity("http://sleuth-trace-two/traceTwo", String.class)
                    .getBody();
        }

    }


    // mian fun ----------------------------------------------------------------

    public static void main(String[] args) {
        new SpringApplicationBuilder(SleuthTraceOneApp.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }


}
