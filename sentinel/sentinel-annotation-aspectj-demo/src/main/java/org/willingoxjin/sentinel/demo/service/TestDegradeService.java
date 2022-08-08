package org.willingoxjin.sentinel.demo.service;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jin.Nie
 * @date 2022-07-22
 */
@Slf4j
@Service
public class TestDegradeService {
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * 	blockHandler: 流控降级的时候进入的兜底函数
     *  fallback: 抛出异常的时候进入的兜底函数
     *  (1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理)
     */
    @SentinelResource(
            value = "org.willingoxjin.sentinel.demo.service.TestDegradeService#degrade",
            entryType = EntryType.OUT,
            blockHandler = "degradeBlockHandler",
            fallback = "degradeFallback")
    public String degrade() {
        log.info("----> 正常执行degrade方法");
        if(count.incrementAndGet() % 3 == 0) {
            throw new RuntimeException("抛出业务异常");
        }
        return "degrade";
    }

    public String degradeBlockHandler(BlockException ex) {
        log.error("----> 执行方法熔断降级处理, ex=[{0}]", ex);
        return "执行 降级流控方法";
    }

    public String degradeFallback(Throwable t) {
        log.error("----> 执行方法异常处理, throwable=[{0}]", t);
        return "执行 异常降级方法";
    }
}
