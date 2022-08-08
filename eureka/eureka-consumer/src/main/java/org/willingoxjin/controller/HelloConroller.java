package org.willingoxjin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2021-12-07
 */
@RequiredArgsConstructor
@Slf4j
@RestController
public class HelloConroller {

    private final LoadBalancerClient client;

    private final RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello() {
        ServiceInstance instance = client.choose("eureka-client");
        if (instance == null) {
            return "No Available Instances";
        }

        String target = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url={}", target);
        String response = restTemplate.getForObject(target, String.class);
        log.info("response={}", response);

        return response;
    }

    @PostMapping("/hello")
    public Map<String, String> helloByPost() {
        ServiceInstance instance = client.choose("eureka-client");
        if (instance == null) {
            log.error("No Available Instances");
            return null;
        }

        String target = String.format("http://%s:%s/sayHi", instance.getHost(), instance.getPort());
        log.info("url={}", target);
        Map<String, String> map = new HashMap<>();
        map.put("name", "张三");
        Map<String, String> response = restTemplate.postForObject(target, map, HashMap.class);
        response.forEach((k,v) -> System.out.println(String.format("k=%s, v=%s", k, v)));
        return response;
    }

}
