package cn.spring.learning.mq.mqtt.producer;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * MQTT 消息回调，收到消息时候，记录日志
 *
 * @author lcs
 * @date 2021/12/3 14:53
 */
@Slf4j
@Component
public class MqttReceiveCallback implements MqttCallback {

    @Override
    public void connectionLost(Throwable throwable) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        log.info("Mqtt Client 接收消息主题: {}", topic);
        log.info("Mqtt Client 接收消息Qos: {}", message.getQos());
        log.info("Mqtt Client 接收消息内容: {}", new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
    }
}
