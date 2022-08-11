package cn.spring.learning.mq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * RocketMQ
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/11 20:34
 */
@SpringBootTest
@ActiveProfiles(value = "home")
public class RocketMqProducerTest {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    @DisplayName(value = "生产消息")
    public void produce() {
        this.rocketMQTemplate.convertAndSend("springboot-rocketmq", "hello springboot mq");
    }
}
