package cn.spring.learning.env;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author Jinhua-Lee
 */
@Slf4j
@Component
public class MyEnvPropInitPrinter implements EnvironmentAware, InitializingBean {

    private ConfigurableEnvironment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        if (!(environment instanceof ConfigurableEnvironment)) {
            throw new IllegalArgumentException("The environment is not an instance of Configurable Environment.");
        }
        this.environment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(environment, "environment is null!");
        log.info("property sources starts...");
        environment.getPropertySources().forEach(propertySource -> {
            System.out.println("propertySource.getName() = " + propertySource.getName());
            System.out.println("propertySource.getSource() = " + propertySource.getSource());
        });

        // log.info("systemc properties starts...");
        // environment.getSystemProperties().forEach((k, v) -> System.out.println(k + " = " + v));
    }
}
