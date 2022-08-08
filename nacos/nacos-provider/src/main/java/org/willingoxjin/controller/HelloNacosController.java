package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2022-04-11
 */
@RestController
public class HelloNacosController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("helloNacos")
    public String helloNacos() {
        return serverPort;
    }

}
