package org.willingoxjin.sentinel.demo.service;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Jin.Nie
 * @date 2022-07-22
 */
@Slf4j
@Service
public class TestFlowService {

    /**
     * 	blockHandler: 流控降级的时的降级处理函数
     *  fallback: 抛出异常的时候进入的函数
     *  (1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理)
     */
    @SentinelResource(
            value = "org.willingoxjin.sentinel.demo.service.TestFlowService#flow",
            entryType = EntryType.OUT,
            blockHandler = "flowBlockHandler",
            fallback = ""
    )
    public String flow() {
        log.info("----> 正常执行flow方法");
        return "flow";
    }

    public String flowBlockHandler(BlockException ex) {
        log.error("----> 触发 流控策略=[{0}]", ex);
        return "执行 流控方法";
    }

}
