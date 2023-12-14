package cn.spring.learning.beans.bean.inject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 复杂类型的注入验证类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/29 19:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "learning.complex")
@Component
@Slf4j
public class ComplexInjectionBean implements InitializingBean {
    private int[] intArrayYml;
    private int[] intArrayProps;
    private List<Integer> integerListCsv;

    @Value(value = "${learning.complex.int-arr-by-value}")
    private int[] intArrByValue;

    private String[] strArrayYml;
    private Map<Integer, String> int2StrYml;

    /**
     * 引用复杂类型
     */
    private InnerInjectionBean innerInjection;

    private List<InnerInjectionBean> innerInjections;

    @Override
    public void afterPropertiesSet() throws Exception {
        ObjectMapper mapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
        log.info("complexInjectionBean信息：{}", mapper.writeValueAsString(this));
    }

    @Data
    public static class InnerInjectionBean {
        private String innerStr;
        private Integer innerInt;
    }
}
