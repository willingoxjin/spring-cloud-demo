package org.willingoxjin.biz;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.willingoxjin.topic.DelayedTopic;
import org.willingoxjin.topic.DlqTopic;
import org.willingoxjin.topic.ErrorTopic;
import org.willingoxjin.topic.FallbackTopic;
import org.willingoxjin.topic.GroupTopic;
import org.willingoxjin.topic.MyTopic;
import org.willingoxjin.topic.RequeueTopic;

/**
 * @author Jin.Nie
 * @date 2022-07-12
 */
@Slf4j
@EnableBinding({
        Sink.class,
        MyTopic.class,
        GroupTopic.class,
        DelayedTopic.class,
        ErrorTopic.class,
        RequeueTopic.class,
        DlqTopic.class,
        FallbackTopic.class,
})
public class StreamConsumer {
    private final AtomicInteger count = new AtomicInteger(0);

    /**
     * Sink.INPUT 是 Stream 提供的默认的信道
     */
    @StreamListener(Sink.INPUT)
    public void consume(String payload) {
        log.info("message consume successfully, payload=[{}]", payload);
    }

    /**
     * 自定义Topic-广播消息
     */
    @StreamListener(MyTopic.INPUT)
    public void consumeMyMessage(String payload) {
        log.info("My message consume successfully, payload=[{}]", payload);
    }

    /**
     * 消息分组 & 消费分区
     */
    @StreamListener(GroupTopic.INPUT)
    public void consumeGroupMessage(String payload) {
        log.info("Group message consumed successfully, payload={}", payload);
    }

    /**
     * 延迟消息
     */
    @StreamListener(DelayedTopic.INPUT)
    public void consumeDelayMessage(String payload) {
        log.info("Delay message consumed successfully, payload={}", payload);
    }

    /**
     * 消息消费模拟异常（消费端重试）
     */
    @StreamListener(ErrorTopic.INPUT)
    public void consumeMessageSimulatingError(String payload) {
        log.info("Message consumed simulating error, payload={}, count=[{}]", payload, count.get());
        if (count.incrementAndGet() >= 3) {
            log.info("Not Bad");
            count.set(0);
        } else {
            log.info("Simulating Exception");
            throw new RuntimeException("I'm not OK");
        }
    }

    /**
     * 消息重回队列测试
     */
    @StreamListener(RequeueTopic.INPUT)
    public void consumeMessageRequeue(String payload) {
        log.info("Message consumed Requeue Test, payload=[{}]", payload);
        try {
            Thread.sleep(3000L);
        } catch (Exception ignored) {
        }
        throw new RuntimeException("I'm not OK");
    }

    /**
     * 死信队列
     */
    @StreamListener(DlqTopic.INPUT)
    public void consumeMessageDlq(String payload) {
        log.info("Message consumed DLQ Test, payload=[{}], count=[{}]", payload, count.get());
        if (count.incrementAndGet() >= 3) {
            log.info("Dlq - Not Bad");
            count.set(0);
        } else {
            log.info("Dlq - Not so good");
            throw new RuntimeException("I'm not OK");
        }
    }

    // Fallback + 升级版本
    @StreamListener(FallbackTopic.INPUT)
    public void consumeMessageFallback(String payload,
                            @Header("version") String version) {
        log.info("Message consumed Fallback Test, payload=[{}], version=[{}]", payload, version);

        if ("1.0".equals(version)) {
            log.info("Fallback - Not Bad");
        } else if ("2.0".equalsIgnoreCase(version)) {
            // 模拟异常
            log.info("unsupported version");
            throw new RuntimeException("I'm not OK");
        } else {
            log.info("Fallback - version={}", version);
        }
    }

    // 降级流程
    @ServiceActivator(inputChannel = "fallback-topic.fallback-group.errors")
    public void fallback(Message<?> message) {
        log.info("fallback entered");
        // 新零售发布库存 - 异步请求
        // 降级： 钉钉群的接口 - 通知运营

        // 强制登出 -> inactive user
        // user表 -> flag (active/inactive) -> 不让你下次登录
    }

}
