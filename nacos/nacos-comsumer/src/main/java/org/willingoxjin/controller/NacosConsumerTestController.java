package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Jin.Nie
 * @date 2021-12-09
 */
@RestController
public class NacosConsumerTestController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/helloNacosTest")
    public String helloNacosTest() {
        ServiceInstance choose = loadBalancerClient.choose("nacos-provider");
        System.out.println("服务地址:" + choose.getUri());
        System.out.println("服务名称:" + choose.getServiceId());
        String s = restTemplate.getForObject(choose.getUri() + "/helloNacos", String.class);
        System.out.println(s);

        return s;
    }

}
