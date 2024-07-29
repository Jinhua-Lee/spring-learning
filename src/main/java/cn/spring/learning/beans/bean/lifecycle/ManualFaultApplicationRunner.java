package cn.spring.learning.beans.bean.lifecycle;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua-Lee
 */
@Component
public class ManualFaultApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        throw new RuntimeException("手动抛错.");
    }
}
