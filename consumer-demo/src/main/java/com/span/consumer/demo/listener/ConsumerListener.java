package com.span.consumer.demo.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Span
 * @date 2020/12/23  12:43
 * @description
 */
@Component
public class ConsumerListener {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "SPRING_RABBIT_QUEUE"),
            exchange = @Exchange(value = "SPRING_RABBIT_EXCHANGE", type = ExchangeTypes.TOPIC, ignoreDeclarationExceptions = "true"),
            key = {"a.b"}
    ))
    public void listener(String msg) {
        System.out.println("消费者收到信息" + msg);
    }
}
