package cn.spring.learning.beans.bean.inject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
@Configuration
public class ComplexInjectionBean {
    private int[] intArrayYml;
    private int[] intArrayProps;
    private List<Integer> integerListCsv;

    @Value(value = "${learning.complex.int-arr-by-value}")
    private int[] intArrByValue;

    private String[] strArrayYml;
    private Map<Integer, String> int2StrYml;
}
