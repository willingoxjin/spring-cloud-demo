package org.willingoxjin.sentinel.demo.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@RestController
public class TestSentinelController {

    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * 测试流控
     */
    @GetMapping("/testFlow")
    public String testFlow() throws InterruptedException {
        try (Entry entry = SphU.entry("org.willingoxjin.sentinel.demo.controller.TestSentinelController#testFlow");) {
            System.out.println("test: 执行访问资源逻辑代码");

            Thread.sleep(20);
        } catch (BlockException e) {
            System.err.println("要访问的资源被流控了, 执行流控逻辑！");
        }
        return "sentinel test";
    }

    /**
     * 测试降级
     */
    @GetMapping("/testDegrade")
    public String degrade(long time) throws Exception {
        try (Entry entry = SphU.entry(
                "org.willingoxjin.sentinel.demo.controller.TestSentinelController#testDegrade");) {
            System.out.println("test: 执行访问资源逻辑代码");
            Thread.sleep(time);
            System.out.println("degrade--> 执行时间：" + time + " ms ");
        } catch (BlockException e) {
            System.err.println("要访问的资源被降级了, 执行降级逻辑！");
        }
        return "degrade test";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}