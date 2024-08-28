package cn.spring.learning.mq.mqtt.producer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author lcs
 * @author Jinhua
 * @date 2022/11/28 10:12
 */
@Slf4j
@Component
public class MqttProducer {

    private MqttClient mqttClient;

    private MqttConnectOptions mqttConnectOptions;

    /**
     * 保存已订阅的主题，订阅过后的主题不再执行订阅
     */
    private final Set<String> subscribedTopics = new ConcurrentSkipListSet<>();

    /**
     * 发布消息
     *
     * @param topic   主题
     * @param message 消息内容
     * @param qos     服务质量？
     */
    public void handleConnStateAndPublish(String topic, byte[] message, int qos) throws MqttException {
        if (Objects.nonNull(mqttClient)) {
            if (!mqttClient.isConnected()) {
                log.warn("mqttClient未连接，进行连接...");
                mqttClient.connect(this.mqttConnectOptions);
            }
            publishMessage(topic, message, qos);
        } else {
            throw new IllegalStateException("mqttClient 为null，无法进行推送，请检查bean注入！");
        }
    }

    private void publishMessage(String topic, byte[] message, int qos) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(message);
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        if (Objects.isNull(mqttTopic)) {
            log.error("topic = {}创建失败", topic);
            return;
        }
        try {
            mqttClient.publish(topic, mqttMessage);
            if (log.isDebugEnabled()) {
                log.debug("消息入库成功, topic={}, body = {}", topic, mqttMessage);
            }
        } catch (Exception e) {
            log.error("消息入库失败,msg = {},body = {}", e.getMessage(), mqttMessage);
        }
    }

    /**
     * 订阅
     *
     * @param topic 主题
     */
    @SuppressWarnings(value = "unused")
    public void subscribeMqttTopicIfNecessary(String topic) {
        if (Objects.nonNull(mqttClient)
                && mqttClient.isConnected()
                && !this.subscribedTopics.contains(topic)) {
            try {
                mqttClient.subscribe(topic, 1);
            } catch (MqttException e) {
                log.error(e.getMessage());
            }
            this.subscribedTopics.add(topic);
        }
    }

    @Autowired
    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Autowired
    public void setMqttConnectOptions(MqttConnectOptions mqttConnectOptions) {
        this.mqttConnectOptions = mqttConnectOptions;
    }
}
