package cn.spring.learning.mq.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jinhua-Lee
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqConfig {
    private String host;
    private int port;
    private String username;
    private String password;
    private String virtualHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(this.host);
        factory.setPort(this.port);
        factory.setUsername(this.username);
        factory.setPassword(this.password);
        factory.setVirtualHost(virtualHost);
        return factory;
    }

    @Bean
    public TopicExchange exA(@Value(value = "topicExA") String exchangeNameA) {
        return new TopicExchange(exchangeNameA, true, false);
    }

    @Bean
    public Queue queueA(@Value(value = "queueA") String queueNameA) {
        return new Queue(queueNameA, true, false, false);
    }

    @Bean
    public Queue queueB(@Value(value = "queueB")String queueNameB) {
        return new Queue(queueNameB, true, false, false);
    }

    @Bean
    public FanoutExchange exAb(@Value(value = "fanExAb") String fanoutExchangeName) {
        return new FanoutExchange(fanoutExchangeName, true, false);
    }

    @Bean
    public Binding oriBinding(Queue queueA, TopicExchange exA, @Value(value = "rkA") String oriRoutingKey) {
        return BindingBuilder.bind(queueA).to(exA).with(oriRoutingKey);
    }

    @Bean
    public Binding transFromBinding(Queue queueA, FanoutExchange exAb) {
        return BindingBuilder.bind(queueA).to(exAb);
    }

    @Bean
    public Binding transToBinding(Queue queueB, FanoutExchange exAb) {
        return BindingBuilder.bind(queueB).to(exAb);
    }
}