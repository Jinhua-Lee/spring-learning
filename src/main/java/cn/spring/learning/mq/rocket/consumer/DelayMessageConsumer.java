package cn.spring.learning.mq.rocket.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua-Lee
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "rocket-delay-group", topic = "rocket-delay")
public class DelayMessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("[delay message consumer] received message: " + message);
    }
}
