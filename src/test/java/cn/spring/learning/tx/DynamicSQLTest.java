package cn.spring.learning.tx;

import cn.spring.learning.tx.entity.Customer;
import cn.spring.learning.tx.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Mybatis的动态SQL测试
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/23 21:27
 */
@SpringBootTest(classes = TxTestApplication.class)
public class DynamicSQLTest {

    @Autowired
    CustomerMapper customerMapper;

    @Test
    public void testQueryCustomerByNameAndAge() {
        List<Customer> customersByNameAndAge = customerMapper.getCustomersByNameAndAge("金华", 25);
        customersByNameAndAge.forEach(System.out::println);
    }
}
