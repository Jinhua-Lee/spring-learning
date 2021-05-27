package cn.demo.springlearning.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 单例Bean
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/27 22:26
 */
@Getter
@Setter
@NoArgsConstructor
public class SingletonBean {

    private Integer id;
    private String name;

    public void init() {
        System.out.println("after construct...");
    }

    public void destroy() {
        System.out.println("before destroy...");
    }
}
