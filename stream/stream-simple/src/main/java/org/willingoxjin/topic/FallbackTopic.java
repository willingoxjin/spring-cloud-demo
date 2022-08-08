package org.willingoxjin.topic;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author Jin.Nie
 * @date 2022-07-13
 */
public interface FallbackTopic {

    String INPUT = "fallback-consumer";

    String OUTPUT = "fallback-producer";

    @Input(INPUT)
    SubscribableChannel input();

    @Output(OUTPUT)
    MessageChannel output();

}
