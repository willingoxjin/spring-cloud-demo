package org.willingoxjin.controller;

import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.willingoxjin.biz.MessageBean;
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
@RestController
public class StreamTestController {

    @Autowired
    private MyTopic myTopicProducer;

    @Autowired
    private GroupTopic groupTopicProducer;

    @Autowired
    private DelayedTopic delayedTopicProducer;

    @Autowired
    private ErrorTopic errorTopicProducer;

    @Autowired
    private RequeueTopic requeueTopicProducer;

    @Autowired
    private DlqTopic dlqTopicProducer;

    @Autowired
    private FallbackTopic fallbackTopicProducer;


    // 简单广播消息
    @PostMapping("send")
    public void sendMessage(@RequestParam String payload) {
        myTopicProducer.output().send(MessageBuilder.withPayload(payload).build());
    }

    // 消息分组和消息分区
    @PostMapping("sendToGroup")
    public void sendMessageToGroup(String payload) {
        groupTopicProducer.output().send(MessageBuilder.withPayload(payload).build());
    }

    // 延迟消息
    @PostMapping("delaySend")
    public void sendDelayMessage(@RequestParam String payload,
                                 @RequestParam @Validated @Min(value = 1) Integer seconds) {
        val message = MessageBuilder.withPayload(payload)
                // 延迟时间
                .setHeader("x-delay", seconds * 1000)
                .build();
        delayedTopicProducer.output().send(message);
        log.info("send delayed message successfully, payload=[{}], x-delay=[{}s]", payload, seconds);
    }

    // 发送消息，模拟本机重试
    @PostMapping("sendError")
    public void sendMessageError(String payload) {
        errorTopicProducer.output().send(MessageBuilder.withPayload(payload).build());
    }

    // 发送消息，重回队列
    @PostMapping("requeue")
    public void sendMessageRequeue(String payload) {
        requeueTopicProducer.output().send(MessageBuilder.withPayload(payload).build());
    }

    // 发送消息，死信队列
    @PostMapping("dlq")
    public void sendMessageDlq(String payload) {
        dlqTopicProducer.output().send(MessageBuilder.withPayload(payload).build());
    }

    // fallback
    @PostMapping("fallback")
    public void sendMessageToFallback(@RequestParam String payload,
                                      @RequestParam(defaultValue = "1.0") String version) {
        fallbackTopicProducer.output().send(
                MessageBuilder.withPayload(payload)
                        .setHeader("version", version)
                        .build());
    }

}
