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
                "ex",
                "rk",
                "test_repeatedly_consume_" + Instant.now().toEpochMilli()
        );
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}