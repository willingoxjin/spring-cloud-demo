package org.willingoxjin.sentinel.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.Collections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@RestController
public class TestSentinelController {

    public static void initFlowRules() {
        FlowRule rule = new FlowRule();
        //	注意： 我们的规则一定要绑定到对应的资源上，通过资源名称进行绑定
        rule.setResource("org.willingoxjin.sentinel.demo.controller.TestSentinelController#testFlow");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(20);
        // 规则管理器
        FlowRuleManager.loadRules(Collections.singletonList(rule));
    }

    /**
     * 测试流控
     */
    @GetMapping("/testFlow")
    public String testFlow() throws InterruptedException {
        initFlowRules();
        try (Entry entry = SphU.entry("org.willingoxjin.sentinel.demo.controller.TestSentinelController#testFlow");) {
            System.out.println("test: 执行访问资源逻辑代码");

            Thread.sleep(20);
        } catch (BlockException e) {
            System.err.println("要访问的资源被流控了, 执行流控逻辑！");
        }
        return "sentinel test";
    }

    @GetMapping("/hello")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }

}