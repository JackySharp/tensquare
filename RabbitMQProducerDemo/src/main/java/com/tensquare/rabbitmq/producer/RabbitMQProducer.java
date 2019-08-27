package com.tensquare.rabbitmq.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //以Direct（直接）模式发送消息
    @Test
    public void testSendDirectMessage() {
        /**
         * 第一个参数exchange：代表转发器，在Direct模式下exchange为空字符串
         * 第二个参数routingkey：代表luyoukey，就是消息名称
         * 第三个参数object：代表发送的消息内容
         */
        rabbitTemplate.convertAndSend("", "test", "发送Direct模式消息");
    }

    //以Fanout（分裂）模式发送消息
    @Test
    public void testSendFanoutMessage() {
        /**
         * 第一个参数exchange：代表转发器
         * 第二个参数routingkey：代表路由key，就是消息名称，分裂模式下为空字符串
         * 第三个参数object：代表发送的消息内容
         */
        rabbitTemplate.convertAndSend("team", "", "发送Fanout模式消息");
    }

    //以Topic（主题）模式发送消息
    @Test
    public void testSendTopicMessageFromWaiter() {
        /**
         * 第一个参数exchange：代表转发器
         * 第二个参数routingkey：代表路由key，就是消息名称
         * 第三个参数object：代表发送的消息内容
         */
        rabbitTemplate.convertAndSend("restaurant","take-out.action","我正在送餐的路上");
    }

    //以Topic（主题）模式发送消息
    @Test
    public void testSendTopicMessageFromCooker() {
        /**
         * 第一个参数exchange：代表转发器
         * 第二个参数routingkey：代表路由key，就是消息名称
         * 第三个参数object：代表发送的消息内容
         */
        rabbitTemplate.convertAndSend("restaurant","cook.right-now","我在出餐的同时保证出品品质");
    }

    //以Topic（主题）模式发送消息
    @Test
    public void testSendTopicMessageFromAccounter() {
        /**
         * 第一个参数exchange：代表转发器
         * 第二个参数routingkey：代表路由key，就是消息名称
         * 第三个参数object：代表发送的消息内容
         */
        rabbitTemplate.convertAndSend("restaurant","cook.action","我负责收钱");
    }

}
