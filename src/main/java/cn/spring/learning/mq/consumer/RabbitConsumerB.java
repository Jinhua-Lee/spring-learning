package cn.spring.learning.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Jinhua-Lee
 */
@Slf4j
@Component
public class RabbitConsumerB {

    @RabbitHandler
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "queueB", durable = "true"),
                    exchange = @Exchange(value = "fanExAb", type = ExchangeTypes.FANOUT),
                    key = "rkAB"
            )
    })
    public void process(Message massage) {
        String msg = new String(massage.getBody(), StandardCharsets.UTF_8);
        log.info("{} 收到消息消息：{}", this.getClass().getName(), msg);
    }
}
