package cn.demo.springlearning.bean.circular;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Todo
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/17 23:28
 */
@Getter
@NoArgsConstructor
@Component
public class BeanC {

    private BeanA a;

    @Autowired
    public void setA(BeanA a) {
        this.a = a;
    }
}
