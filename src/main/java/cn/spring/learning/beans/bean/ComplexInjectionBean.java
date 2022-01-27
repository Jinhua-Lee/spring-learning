package cn.spring.learning.beans.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class ComplexInjectionBean {
    private int[] intArray;
    private List<Integer> integers;
    private Map<Integer, String> int2Str;
}
