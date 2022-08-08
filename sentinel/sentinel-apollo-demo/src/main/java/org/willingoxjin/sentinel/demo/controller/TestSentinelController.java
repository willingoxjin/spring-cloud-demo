package org.willingoxjin.sentinel.demo.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.sentinel.demo.config.JavaConfigBean;

/**
 * @author Jin.Nie
 * @date 2022-07-24
 */
@RestController
public class TestSentinelController {

    @Autowired
    private JavaConfigBean javaConfigBean;

    @SentinelResource(value = "org.willingoxjin.sentinel.demo.controller.TestSentinelController#test")
    @GetMapping("/test")
    public String test() {
        System.err.println("timeout: " + javaConfigBean.getTimeout());
        System.err.println("newKey: " + javaConfigBean.getNewKey());
        return "index";
    }

}
