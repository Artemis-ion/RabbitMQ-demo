package com.span.producer.demo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerDemoApplicationTests {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void sendMsg() {
        //.convertAndSend(交换机，路由键，传输的数据)
       rabbitTemplate.convertAndSend("SPRING_RABBIT_EXCHANGE", "a.b", "hello spring rabbit");
       System.out.println("生产者已经发送消息");
    }

}
