package cn.spring.learning.mq.mqtt.producer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
                reConnectMqtt();
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
                log.debug("消息入库成功,topic={},body={}", topic, mqttMessage);
            }
        } catch (Exception e) {
            log.error("消息入库失败,msg={},body={}", e.getMessage(), mqttMessage);
        }
    }

    /**
     * 重新连接
     */
    public void reConnectMqtt() throws MqttException {
        mqttClient.connect(mqttConnectOptions);
    }

    /**
     * 清除
     *
     * @param topic 主题
     */
    @SuppressWarnings("unused")
    public void unsubscribeMqttTopic(String topic) {
        if (Objects.nonNull(mqttClient) && !mqttClient.isConnected()) {
            try {
                mqttClient.unsubscribe(topic);
            } catch (MqttException e) {
                log.error(e.getMessage());
            }
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
