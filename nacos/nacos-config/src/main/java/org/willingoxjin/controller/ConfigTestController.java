package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2022-04-12
 */
@RefreshScope
@RestController
@RequestMapping("/configTest")
public class ConfigTestController {

    @Value("${testNacosMsg}")
    private String testNacosMsg;

    @Value("${testNacosPropOrder1}")
    public String testNacosPropOrder1;

    @Value("${testNacosPropOrder2}")
    public String testNacosPropOrder2;

    @Value("${testNacosPropOrder3}")
    public String testNacosPropOrder3;

    @Value("${testNacosPropOrder4}")
    public String testNacosPropOrder4;

    @Value("${commonConnectTimeout}")
    public String commonConnectTimeout;

    @Value("${mysqlConnectTimeout}")
    public String mysqlConnectTimeout;

    @Value("${redisConnectTimeout}")
    public String redisConnectTimeout;

    @RequestMapping("/get")
    public String get() {
        return testNacosMsg;
    }

    @RequestMapping("/getTestNacosPropOrder")
    public Map<String, String> gettestNacosPropOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("testNacosPropOrder1", testNacosPropOrder1);
        map.put("testNacosPropOrder2", testNacosPropOrder2);
        map.put("testNacosPropOrder3", testNacosPropOrder3);
        map.put("testNacosPropOrder4", testNacosPropOrder4);

        return map;
    }

    @RequestMapping("/getSharedConfigs")
    public Map<String, String> getSharedConfigs() {
        Map<String, String> map = new HashMap<>();
        map.put("commonConnectTimeout", commonConnectTimeout);
        map.put("mysqlConnectTimeout", mysqlConnectTimeout);
        return map;
    }


    @RequestMapping("/getExtensionConfigs")
    public Map<String, String> getExtensionConfigs() {
        Map<String, String> map = new HashMap<>();
        map.put("commonConnectTimeout", commonConnectTimeout);
        map.put("redisConnectTimeout", redisConnectTimeout);
        return map;
    }

}
