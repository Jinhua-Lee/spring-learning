package cn.spring.learning.mq.mqtt.producer;

import cn.spring.learning.mq.mqtt.config.MqttConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MqttProducerTest {

    private MqttProducer mqttProducer;
    private MqttConfig mqttConfig;

    @Test
    @DisplayName(value = "测试发送给Mqtt指定消息")
    public void testSendMqttMessage() {
        this.mqttProducer.initMqttClient();
        String staticTopic = mqttConfig.getHonorMqsUniqueTopic();
        String helloMsg = "hello, mqtt!";
        this.mqttProducer.constructAndSubscribeAndPublishMqttMessage(
                staticTopic,
                helloMsg.getBytes(StandardCharsets.UTF_8),
                mqttConfig.getQos()
        );
        this.mqttProducer.closePersistenceAndConnect();
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