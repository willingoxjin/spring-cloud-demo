package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.service.IService;

/**
 * @author Jin.Nie
 * @date 2022-01-08
 */
@RestController
public class HiFeignController {

    @Autowired
    private IService service;

    @GetMapping("hiFeign")
    public String hiFeign() {
        return service.sayHi();
    }

}
