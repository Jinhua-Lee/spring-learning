package cn.demo.springlearning.test.bean.circular;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Todo
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/9/17 23:28
 */
@Data
@NoArgsConstructor
//@AllArgsConstructor
public class BeanC {

    private BeanA a;
}
