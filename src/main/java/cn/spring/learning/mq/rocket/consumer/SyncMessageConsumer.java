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
@RocketMQMessageListener(consumerGroup = "rocket-sync-group", topic = "rocket-sync")
public class SyncMessageConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("[sync message consumer] received message: " + message);
    }
}
