package cn.spring.learning.mq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

/**
 * @author Jinhua-Lee
 */
@RestController
public class RabbitProducer {

    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public void sendMsg() {
        rabbitTemplate.convertAndSend(
                // 原有topic交换机，相同routingKey实现多队列同时消费功能
                "topicExA",
                // 通过新的fanout交换机，才能实现多队列同时消费功能
                //"fanExAb",
                "rkA",
                "test_repeatedly_consume_" + Instant.now().toEpochMilli()
        );
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
