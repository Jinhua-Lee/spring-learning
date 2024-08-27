package cn.spring.learning.mq.mqtt.producer;

import cn.spring.learning.mq.mqtt.config.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

@Slf4j
@SpringBootTest
public class MqttProducerTest {

    private MqttProducer mqttProducer;
    private MqttConfig mqttConfig;

    @Test
    @DisplayName(value = "mqtt simple message.")
    public void testSendMqttMessage() throws MqttException {
        String staticTopic = mqttConfig.getHonorMqsUniqueTopic();
        String helloMsg = "hello, mqtt!";
        this.mqttProducer.handleConnStateAndPublish(
                staticTopic,
                helloMsg.getBytes(StandardCharsets.UTF_8),
                mqttConfig.getQos()
        );
    }

    @Test
    @DisplayName(value = "publish while subscribing")
    public void testPublishWhileSubscribing() {
        // 现场出现死锁，GPT解答是订阅和发布过程存在资源冲突，并发情况下可能死锁
        // 现在模拟并发场景，观察是否会出现死锁
        ExecutorService executor = new ThreadPoolExecutor(
                8, 16, 10_000L, TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );

        for (int i = 0; i < 100; i++) {
            executor.submit(() -> {
                String staticTopic = mqttConfig.getHonorMqsUniqueTopic();
                String helloMsg = "hello, mqtt!";
                try {
                    this.mqttProducer.handleConnStateAndPublish(
                            staticTopic,
                            helloMsg.getBytes(StandardCharsets.UTF_8),
                            mqttConfig.getQos()
                    );
                } catch (MqttException e) {
                    log.error("", e);
                }
            });
        }
    }

    @Autowired
    public void setMqttProducer(MqttProducer mqttProducer) {
        this.mqttProducer = mqttProducer;
    }

    @Autowired
    public void setMqttConfig(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }
}