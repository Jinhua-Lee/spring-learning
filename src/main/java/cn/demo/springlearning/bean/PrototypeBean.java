package cn.demo.springlearning.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 原型Bean
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 22:27
 */
@Getter
@Setter
@NoArgsConstructor
public class PrototypeBean {
    private Integer id;
    private String name;
}
