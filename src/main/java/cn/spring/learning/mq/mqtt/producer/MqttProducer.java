package cn.spring.learning.mq.mqtt.producer;

import cn.spring.learning.mq.mqtt.config.MqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    private MqttConfig mqttConfig;

    private MqttClient mqttClient;
    private MemoryPersistence memoryPersistence;
    private MqttConnectOptions mqttConnectOptions;

    /**
     * 保存已订阅的主题，订阅过后的主题不再执行订阅
     */
    private final Set<String> subscribedTopics = new ConcurrentSkipListSet<>();

    @Bean
    public MqttClient getMqttClient() {
        return initMqttClient();
    }

    public MqttClient initMqttClient() {
        mqttConnectOptions = new MqttConnectOptions();
        // 初始化mqtt
        // true可以安全使用内存持久性作为客户端断开连接时清除的所有状态
        mqttConnectOptions.setCleanSession(true);
        // 连接超时
        mqttConnectOptions.setConnectionTimeout(30);
        // 持久化方式
        memoryPersistence = new MemoryPersistence();

        if (Objects.nonNull(mqttConfig.getClientId())) {
            try {
                mqttClient = new MqttClient(mqttConfig.getNameSrvAddr(), mqttConfig.getClientId(), memoryPersistence);
            } catch (MqttException e) {
                if (log.isErrorEnabled()) {
                    log.error(e.getMessage());
                }
            }
        }
        // 连接和回调
        if (Objects.nonNull(mqttClient)) {
            if (mqttConfig.getPrintLog()) {
                MqttReceiveCallback mqttReceiveCallback = new MqttReceiveCallback();
                mqttClient.setCallback(mqttReceiveCallback);
            }
            try {
                mqttClient.connect(mqttConnectOptions);
            } catch (MqttException e) {
                log.error(e.getMessage());
            }
        }
        return mqttClient;
    }

    @SuppressWarnings("unused")
    public void closePersistenceAndConnect() {
        // 关闭存储方式
        if (Objects.nonNull(memoryPersistence)) {
            try {
                memoryPersistence.close();
            } catch (MqttPersistenceException e) {
                log.error(e.getMessage());
            }
        }
        // 关闭连接
        if (Objects.nonNull(mqttClient)) {
            if (mqttClient.isConnected()) {
                try {
                    mqttClient.disconnect();
                    mqttClient.close();
                } catch (MqttException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 发布消息
     *
     * @param topic   主题
     * @param message 消息内容
     * @param qos     服务质量？
     */
    public void constructAndSubscribeAndPublishMqttMessage(String topic, byte[] message, int qos) {
        if (Objects.nonNull(mqttClient) && mqttClient.isConnected()) {
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setQos(qos);
            mqttMessage.setPayload(message);
            MqttTopic mqttTopic = mqttClient.getTopic(topic);
            if (Objects.isNull(mqttTopic)) {
                log.error("topic = {}创建失败", topic);
                return;
            }
            try {
                subscribeMqttTopicIfNecessary(topic);
                mqttClient.publish(topic, mqttMessage);
                if (log.isDebugEnabled()) {
                    log.debug("消息入库成功,topic={},body={}", topic, mqttMessage);
                }
                return;
            } catch (Exception e) {
                log.error("消息入库失败,msg={},body={}", e.getMessage(), mqttMessage);
            }
        }
        reConnectMqtt();
    }

    /**
     * 重新连接
     */
    public void reConnectMqtt() {
        if (Objects.nonNull(mqttClient)) {
            if (Objects.nonNull(mqttConnectOptions)) {
                try {
                    mqttClient.connect(mqttConnectOptions);
                } catch (MqttException e) {
                    log.error(e.getMessage());
                }
            }
        } else {
            initMqttClient();
        }
    }

    /**
     * 订阅
     *
     * @param topic 主题
     */
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
                subscribedTopics.remove(topic);
            } catch (MqttException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Autowired
    public void setMqttConfig(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }
}
