package org.willingoxjin.sentinel.demo.config;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Jin.Nie
 * @date 2022-07-15
 */
@ControllerAdvice
@Order(0)
public class SentinelBlockExceptionHandlerConfig {

    @ExceptionHandler(BlockException.class)
    @ResponseBody
    public String sentinelBlockHandler(BlockException e) {
        AbstractRule rule = e.getRule();

        System.out.println("Blocked by Sentinel:" + rule.toString());
        return "Blocked by Sentinel";
    }
}