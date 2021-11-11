package cn.demo.springlearning.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 【原型Bean】<p>&emsp;
 * 每次根据原型克隆出新的对象
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 22:27
 */
@Getter
@Setter
@Component
@Scope(scopeName = "prototype")
public class PrototypeBean {
    private Integer id;
    private String name;
}
