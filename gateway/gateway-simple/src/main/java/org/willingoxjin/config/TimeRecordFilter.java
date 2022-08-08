package org.willingoxjin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Jin.Nie
 * @date 2022-02-25
 */
@Slf4j
@Component
public class TimeRecordFilter implements GatewayFilter, Ordered {

    /**
     * 自定义过滤器 — 计时
     * @param exchange 请求内容
     * @param chain 过滤器相关
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        StopWatch timer = new StopWatch();
        String taskName = exchange.getRequest().getURI().getRawPath();
        timer.start(taskName); /* taskName 是打印日志中的名称 */
        exchange.getAttributes().put("requestTimeBegin", System.currentTimeMillis());
        // then 中类似 JavaScript 中的异步回调
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            timer.stop();
            log.info("TimeRecordFilter: {}", timer.prettyPrint());
        }));
    }

    /**
     * 过滤器加载的顺序，这里使用默认的
     * 优先级越高，数字越小
     */
    @Override
    public int getOrder() {
        return 0;
    }

}
