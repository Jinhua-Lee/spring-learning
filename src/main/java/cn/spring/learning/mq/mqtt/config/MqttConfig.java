package cn.spring.learning.mq.mqtt.config;

import lombok.Data;
import lombok.ToString;
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
@ToString
public class MqttConfig {

    private String nameSrvAddr = "";
    private String clientId = "";
    private Integer qos;
    private Boolean printLog = true;
    private String logPath;
    private String honorMqsUniqueTopic;
}
