package com.tensquare.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 使用@Component注解将监听器放入SpringBoot容器，实现SpringBoot启动后监听器也随之启动直到SpringBoot停止，
 * 保证监听器在SprigBoot生命周期一直处于运行状态
 */
@Component
@RabbitListener(queues = "accounter")
public class TopicMessageListener3 {

    @RabbitHandler
    public void recieveTopicMessage(String message) {
        System.out.println("我是会计,我接收到消息:" + message);
    }
}
