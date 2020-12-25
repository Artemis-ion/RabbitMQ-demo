package com.span.producer.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author Span
 * @date 2020/12/24  15:43
 * @description
 */
@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 为了让这个方法生效，使用@PostConstruct注解
     */
    @PostConstruct
    public void init() {
        //不管消息有没有到达交换机，都执行该回调
        //需要的参数是一个函数式接口，使用lambda表达式
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                //如果没有到达交换机
                log.error("消息没有到达交换机, 响应数据是{}, 错误原因是{}", correlationData, cause);
            } else {
                //消息到达队列
                log.info("消息到达队列！！！");
            }

        });

        //如果消息没有达到队列，才会执行
        rabbitTemplate.setReturnCallback((message,replyCode,replyText, exchange, routingKey)-> {
            //message返回的是一个二进制数组
            log.error("消息没有到达队列, 交换机:{}, 路由键：{}, 返回的文字：{}, 消息内容：{}"
                    , exchange, routingKey, replyText, new String(message.getBody()));
        });
    }


    /**
     * 创建死信队列
     * 编码的形式来创建死信队列要在生产方
     */
    @Bean
    public Queue queue() {
        HashMap<String, Object> argumentes = new HashMap<>();
        //argumentes.put("x-d ue2", true, false, false, );
        return new Queue("111");
    }
}
