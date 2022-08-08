package org.willingoxjin.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2022-02-15
 */
@RestController
@RequestMapping("/refreshTest")
@RefreshScope
public class RefreshTestController {

    @Value("${myMotto}")
    private String motto;

    @GetMapping("/motto")
    public String getMotto() {
        return motto;
    }

}
