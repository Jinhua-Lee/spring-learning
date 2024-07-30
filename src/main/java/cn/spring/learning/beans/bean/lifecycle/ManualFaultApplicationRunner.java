package cn.spring.learning.beans.bean.lifecycle;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua-Lee
 */
@Component
@ConditionalOnProperty(prefix = "learning.beans.manual-fault", name = "enable", havingValue = "true")
public class ManualFaultApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        throw new RuntimeException("手动抛错.");
    }
}
