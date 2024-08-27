package cn.spring.learning.mq.mqtt.config;

import cn.spring.learning.mq.mqtt.producer.MqttReceiveCallback;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author Jinhua-Lee
 */
@Slf4j
@Configuration
public class MqttClientConfig {

    @Bean
    public MqttClient mqttClient(MqttConfig mqttConfig,
                                 MqttConnectOptions mqttConnectOptions,
                                 MemoryPersistence memoryPersistence,
                                 MqttReceiveCallback mqttReceiveCallback) throws MqttException {
        MqttClient mqttClient;
        if (Objects.nonNull(mqttConfig.getClientId())) {
            mqttClient = new MqttClient(mqttConfig.getNameSrvAddr(), mqttConfig.getClientId(), memoryPersistence);
        } else {
            throw new IllegalArgumentException(
                    String.format("clientId不能为空，请检查mqtt配置：mqttConfig = %s", mqttConfig)
            );
        }
        // 连接和回调
        if (mqttConfig.getPrintLog()) {
            mqttClient.setCallback(mqttReceiveCallback);
        }
        mqttClient.connect(mqttConnectOptions);
        return mqttClient;
    }

    @Bean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // 初始化mqtt
        // true可以安全使用内存持久性作为客户端断开连接时清除的所有状态
        options.setCleanSession(true);
        // 连接超时
        options.setConnectionTimeout(30);
        // 持久化方式
        return options;
    }

    @Bean
    public MemoryPersistence memoryPersistence() {
        return new MemoryPersistence();
    }
}
