package cn.spring.learning.mq.rocket.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * rocketmq 生产者测试类
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2023/3/18 23:41
 */
@RestController(value = "/mq/rocket/producer")
public class ProducerController {

    @SuppressWarnings(value = "all")
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping(value = "/test")
    public void testProduce() {
        this.rocketMQTemplate.convertAndSend("springboot-rocketmq", "hello springboot mq");
    }

    @Autowired
    @SuppressWarnings(value = "all")
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
