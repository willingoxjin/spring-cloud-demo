package org.willingoxjin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jin.Nie
 * @date 2021-12-26
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class RibbonController {

    private final RestTemplate restTemplate;

    @GetMapping("/helloRibbon")
    public String hello() {
        return restTemplate.getForObject("http://eureka-client/helloRibbon", String.class);
    }

}
