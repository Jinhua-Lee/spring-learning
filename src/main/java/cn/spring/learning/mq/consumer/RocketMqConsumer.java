package cn.spring.learning.mq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/11 20:38
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "group2", topic = "springboot-rocketmq")
public class RocketMqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String msg) {
        log.info("[mq consumer] received the msg: {}", msg);
    }
}
