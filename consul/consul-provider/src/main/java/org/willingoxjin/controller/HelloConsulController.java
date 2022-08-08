package org.willingoxjin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2021-12-09
 */
@RestController
public class HelloConsulController {

    @GetMapping("/helloConsul")
    public String hello() {
        return "helloConsul";
    }

}
