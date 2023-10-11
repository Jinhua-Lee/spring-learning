package cn.spring.learning.beans.bean.inject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 初始化赋值，spring bean创建也会生效
 *
 * @author Jinhua-Lee
 */
@Slf4j
@Data
@Component
public class PropDefaultValueInjectionBean implements InitializingBean {

    private int intValue = 1;
    private Integer integerValue = 11;

    @Override
    public void afterPropertiesSet() throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.info("Value注解注入属性：" + mapper.writeValueAsString(this));
    }
}
