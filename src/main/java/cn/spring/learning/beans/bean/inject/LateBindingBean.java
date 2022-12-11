package cn.spring.learning.beans.bean.inject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * 后期绑定Bean的属性：运行时找bean去注入<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/11 15:15
 */
@Slf4j
@Data
@Component
public class LateBindingBean implements InitializingBean {

    /**
     * - 优：保证一致性.<p>
     * - 劣：会让引用的链路更长.
     */
    @Value(value = "#{valueInputBean.decimalVal}")
    private BigDecimal decimalVal;

    @Override
    public void afterPropertiesSet() {
        log.info("decimalVal = {}", this.decimalVal);
    }
}
