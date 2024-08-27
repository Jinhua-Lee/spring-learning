package cn.spring.learning.mq.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/11/29 10:06
 */
@Data
@Component
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {

    private String nameSrvAddr = "";
    private String clientId = "";
    private Integer qos;
    private Boolean printLog = true;
    private String logPath;
    private String honorMqsUniqueTopic;
}
