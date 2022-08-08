package org.willingoxjin.sentinel.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.sentinel.demo.service.TestDegradeService;
import org.willingoxjin.sentinel.demo.service.TestFlowService;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@RestController
public class TestSentinelAnnotationController {

    @Autowired
    private TestFlowService testFlowService;

    @Autowired
    private TestDegradeService testDegradeService;

    @GetMapping("/testFlow")
    public String testFlow() {
        return testFlowService.flow();
    }

    @GetMapping("/testDegrade")
    public String testDegrade() {
        return testDegradeService.degrade();
    }

}
