package cn.spring.learning.mq.rocket.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

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

    private static final String SIMPLE_TOPIC = "rocket-simple";
    private static final String SYNC_TOPIC = "rocket-sync";
    private static final String ASYNC_TOPIC = "rocket-async";
    private static final String DELAY_TOPIC = "rocket-delay";
    private static final String ORDER_TOPIC = "rocket-order";

    @GetMapping(value = "/simple")
    public void testProduce() {
        this.rocketMQTemplate.convertAndSend(SIMPLE_TOPIC, "hello springboot rocket");
    }

    @GetMapping(value = "/sync")
    public void syncProduce() {
        final int syncNum = 5;
        for (int i = 0; i < syncNum; i++) {
            Message sycMessage = new Message(SYNC_TOPIC, "sync-tag",
                    ("rocket-sync_" + i).getBytes(StandardCharsets.UTF_8)
            );
            // 利用producer进行发送，并同步等待发送结果
            SendResult syncSendResult = rocketMQTemplate.syncSend(SYNC_TOPIC, sycMessage);
            // FIXME: 2023/3/25 同步发送方式请务必捕获发送异常，并做业务侧失败兜底逻辑
            // FIXME: 2023/3/25 SendStatus = SLAVE_NOT_AVAILABLE，消息未能被从节点接收
            log.info("syncSendResult = {}", syncSendResult);
        }
    }

    @GetMapping(value = "/async")
    public void asyncSend() {
        final int asyncNum = 5;
        for (int i = 0; i < asyncNum; i++) {
            Message asyncMessage = new Message(ASYNC_TOPIC, "async-tag",
                    ("rocket-async_" + i).getBytes(StandardCharsets.UTF_8)
            );
            // 利用producer进行发送，并同步等待发送结果
            rocketMQTemplate.asyncSend(ASYNC_TOPIC, asyncMessage, new MyAsyncCallback());
        }
    }

    private static class MyAsyncCallback implements SendCallback {

        @Override
        public void onSuccess(SendResult sendResult) {
            switch (sendResult.getSendStatus()) {
                case FLUSH_DISK_TIMEOUT:
                case FLUSH_SLAVE_TIMEOUT:
                case SLAVE_NOT_AVAILABLE:
                    // 业务回滚操作等.
                    log.error("[msg consume callback] msgId = {}, sendStatus = {}",
                            sendResult.getMsgId(), sendResult.getSendStatus()
                    );
                    break;
                case SEND_OK:
                default:
                    log.info("[msg consume callback] msgId = {}, sendStatus = {}",
                            sendResult.getMsgId(), sendResult.getSendStatus()
                    );
                    break;
            }
        }

        @Override
        public void onException(Throwable e) {
            // 业务回滚操作等.
            log.error("[msg consume callback] an exception occurred when message was asynchronously consumed." +
                    " message = {}", e.getMessage()
            );
        }
    }

    @GetMapping(value = "/delay")
    public void delayMessage() {
        Message delayMessage = new Message(DELAY_TOPIC, "delay-tag",
                ("rocket-delay-" + new SecureRandom().nextInt())
                        .getBytes(StandardCharsets.UTF_8)
        );
        // 延迟等级
        //  3 -> 10s
        //  4 -> 30s
        delayMessage.setDelayTimeLevel(4);
        // 利用producer进行发送，并同步等待发送结果
        rocketMQTemplate.syncSend(DELAY_TOPIC, delayMessage);
    }

    @GetMapping(value = "/order")
    public void orderMessage() {
        final int orderMsgNum = 4;
        String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD"};
        for (int i = 0; i < orderMsgNum; i++) {
            Message msg = new Message(ORDER_TOPIC, tags[i % tags.length],
                    ("rocket-order_" + i).getBytes(StandardCharsets.UTF_8)
            );
            rocketMQTemplate.syncSendOrderly(ORDER_TOPIC, msg, i + "");
        }
    }

    @Autowired
    @SuppressWarnings(value = "all")
    public void setRocketMQTemplate(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }
}
