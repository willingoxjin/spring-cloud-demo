package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2021-12-26
 */
@RestController
public class HelloRibbonController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/helloRibbon")
    public String helloRibbon() {
        return port;
    }

}
