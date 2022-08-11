package cn.spring.learning.beans.bean.circular.plain;

import lombok.*;
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
public class BeanA {

    private BeanB b;

    @Autowired
    public void setB(BeanB b) {
        this.b = b;
    }
}