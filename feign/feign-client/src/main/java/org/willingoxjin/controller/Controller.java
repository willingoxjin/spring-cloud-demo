package org.willingoxjin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.api.IService;

import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2022-01-14
 */
@Slf4j
@RestController
public class Controller implements IService {

    @Value("${server.port}")
    private String port;

    @Override
    public String sayHi(@RequestParam(required = false) String name) {
        return "THIS FEIGN CLIENT SERVER PORT IS " + port;
    }

    @Override
    public Map<String, String> sayHiPost(Map<String, String> map) {
        return map;
    }

    @Override
    public String simulationError() {
        throw new RuntimeException("black sheep");
    }

    @Override
    public String timeoutRetry(@RequestParam("time")Integer time) {
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e) {
        }
        log.info("retry " + port);
        return port;
    }

    @Override
    public String hello(@RequestParam(name = "name", required = false) String name) {
        return name;
    }

    @Override
    public String fallbackTest() {
        return port;
    }
}
