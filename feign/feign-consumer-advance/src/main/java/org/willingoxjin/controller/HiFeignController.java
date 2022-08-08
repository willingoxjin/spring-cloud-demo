package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.willingoxjin.api.IService;

import java.util.Map;

/**
 * @author Jin.Nie
 * @date 2022-01-08
 */
@RestController
public class HiFeignController {

    @Autowired
    private IService service;

    @GetMapping("/sayHiByConsumer")
    public String sayHiByConsumer(@RequestParam(required = false) String name) {
        return service.sayHi(name);
    }

    @PostMapping("/sayHiPostByConsumer")
    public Map<String, String> sayHiPostByConsumer(@RequestBody Map<String, String> map) {
        return service.sayHiPost(map);
    }

}
