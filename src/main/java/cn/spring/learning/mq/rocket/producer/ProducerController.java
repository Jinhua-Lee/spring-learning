package cn.spring.learning.mq.rocket.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * rocketmq 生产者测试类
 *
 * @author Jinhua-Lee
 * @version 1.0
 * @date 2023/3/18 23:41
 */
@Slf4j
@RestController
@RequestMapping(value = "/mq/rocket/producer")
public class ProducerController {

    @SuppressWarnings(value = "all")
    private RocketMQTemplate rocketMQTemplate;

    private static final String SYNC_TOPIC = "rocket-sync";

    @GetMapping(value = "/test")
    public void testProduce() {
        this.rocketMQTemplate.convertAndSend("springboot-rocketmq", "hello springboot mq");
    }

    @GetMapping(value = "/sync")
    public void syncProduce() {
        final int syncNum = 10;
        for (int i = 0; i < syncNum; i++) {
            Message sycMessage = new Message(SYNC_TOPIC, "sync-tag",
                    ("Hello RocketMQ " + i).getBytes(StandardCharsets.UTF_8)
            );
            // 利用producer进行发送，并同步等待发送结果
            SendResult syncSendResult = rocketMQTemplate.syncSend(SYNC_TOPIC, sycMessage);
            // FIXME: 2023/3/25 同步发送方式请务必捕获发送异常，并做业务侧失败兜底逻辑
            // FIXME: 2023/3/25 SendStatus = SLAVE_NOT_AVAILABLE，消息未能同步接收
            log.info("syncSendResult = {}", syncSendResult);
        }
    }

    @Autowired
    @SuppressWarnings(value = "all")
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
