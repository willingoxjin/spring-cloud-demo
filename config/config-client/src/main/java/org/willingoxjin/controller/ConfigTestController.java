package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin.Nie
 * @date 2022-02-16
 */
@RestController
@RequestMapping("configTest")
public class ConfigTestController {

    @Value("${name}")
    private String name;

    @Value("${myMotto}")
    private String motto;

    @Value("${hi}")
    private String hi;

    @Value("${test}")
    private String test;


    @GetMapping("config")
    public List<String> getConfig() {
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(motto);
        list.add(hi);
        list.add(test);

        return list;
    }

}
