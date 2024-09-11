package cn.spring.learning.beans.bean.inject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/7 15:43
 */
@Slf4j
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ValueInputBean implements InitializingBean {

    @Value(value = "${learning.val.strVal: 'hi'}")
    private String strVal;

    @Value(value = "${learning.val.intVal: 7}")
    private Integer intVal;

    @Value(value = "${learning.val.decimalVal: 6.6}")
    private BigDecimal decimalVal;

    @Value(value = "${learning.val.boolVal: true}")
    private Boolean boolVal;

    @Value(value = "${learning.val.strEmptyVal:}")
    private String strEmptyVal;

    @Override
    public void afterPropertiesSet() throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.info("Value注解注入属性：" + mapper.writeValueAsString(this));
    }
}
