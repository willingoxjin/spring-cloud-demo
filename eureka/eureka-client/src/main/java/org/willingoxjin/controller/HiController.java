package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Jin.Nie
 * @date 2021-12-07
 */
@RestController
public class HiController {

    @Value("${server.port}")
    private String port;

    @GetMapping("sayHi")
    public String sayHi(@RequestParam(required = false, defaultValue = "") String name) {
        return "Hi ! " + name;
    }

    @PostMapping("sayHi")
    public Map<String, String> sayHi(@RequestBody HashMap<String, String> map) {
        return map;
    }

}
