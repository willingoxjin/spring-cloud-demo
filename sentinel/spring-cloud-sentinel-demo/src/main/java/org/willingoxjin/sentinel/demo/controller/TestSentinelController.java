package org.willingoxjin.sentinel.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2022-07-22
 */
@RestController
public class TestSentinelController {

    @GetMapping("/en/hello")
    public String enHello() {
        return "hello !";
    }

    @GetMapping("/zh/hello")
    public String zhHello() {
        return "你好！";
    }

}
