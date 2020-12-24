package com.span.consumer.demo.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
    public void listener(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("消费者收到信息" + msg);

            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            channel.basicAck(deliveryTag, true);
            //设置异常
            //int i = 1/0;
            System.out.println("=================");
        } catch (Exception e) {

            e.printStackTrace();
            //获取重复投递的状态
            if (message.getMessageProperties().getRedelivered()){
                //如果重复投递过了，就不重试，直接拒绝  队列中的消息直接清空
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                //如果没有重复投递，就重试
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }

        }
    }
}
