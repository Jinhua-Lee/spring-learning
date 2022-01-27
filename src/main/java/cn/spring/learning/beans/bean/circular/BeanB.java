package cn.spring.learning.beans.bean.circular;

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
public class BeanB {

    private BeanC c;

    @Autowired
    public void setC(BeanC c) {
        this.c = c;
    }
}
