package cn.spring.learning.mq.rabbit.config;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultSaslConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.ConnectionFactoryCustomizer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Collections;

@Slf4j
//@Component
public class ConnectionFactoryCustomizerImpl implements ConnectionFactoryCustomizer {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Override
    @SneakyThrows
    public void customize(ConnectionFactory factory) {
        factory.useSslProtocol(sslContext());
        factory.setSaslConfig(DefaultSaslConfig.EXTERNAL);
    }

    private SSLContext sslContext() throws Exception {
        // 加载信任库
        KeyStore trustStore = KeyStore.getInstance("PKCS12");
        try (InputStream trustStream = getClass().getClassLoader().getResourceAsStream("certs/truststore.p12")) {
            trustStore.load(trustStream, rabbitProperties.getSsl().getTrustStorePassword().toCharArray());
        }
        log.warn("TrustStore Path: {}", getClass().getResource("/certs/truststore.p12"));
        log.warn("TrustStore Aliases: {}", Collections.list(trustStore.aliases()));

        // 加载密钥库
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (InputStream keyStream = getClass().getClassLoader().getResourceAsStream("certs/client_keystore.p12")) {
            keyStore.load(keyStream, rabbitProperties.getSsl().getKeyStorePassword().toCharArray());
        }
        log.warn("KeyStore Path: {}", getClass().getResource("/certs/client_keystore.p12"));
        log.warn("KeyStore Aliases: {}", Collections.list(keyStore.aliases()));

        // 初始化信任管理器
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        // 初始化密钥管理器
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, rabbitProperties.getSsl().getKeyStorePassword().toCharArray());

        // 初始化SSL上下文
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        return sslContext;
    }
}
