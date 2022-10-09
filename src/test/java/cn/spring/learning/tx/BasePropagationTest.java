package cn.spring.learning.tx;

import cn.spring.learning.tx.entity.Commodity;
import cn.spring.learning.tx.entity.Customer;
import cn.spring.learning.tx.service.PropagationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 事务传播行为测试类<p>&emsp;
 * 必须在容器环境下，Junit方法的Transactional注解才可生效，否则需要多一层
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:31
 */
@SpringBootTest(classes = TxApplication.class)
public abstract class BasePropagationTest {

    protected PropagationService propagationService;

    protected List<Commodity> buildCommodities() {
        Commodity c1 = Commodity.builder()
                .name("哈密瓜")
                .price(BigDecimal.valueOf(15))
                .produceCity("新疆")
                .build();
        Commodity c2 = Commodity.builder()
                .name("芒果")
                .price(BigDecimal.valueOf(12))
                .produceCity("三亚")
                .build();
        return Arrays.asList(c1, c2);
    }

    protected List<Customer> buildCustomers() {
        Customer cus1 = Customer.builder()
                .name("小明")
                .gender("男")
                .age(18)
                .build();
        Customer cus2 = Customer.builder()
                .name("小红")
                .gender("女")
                .age(20)
                .build();
        return Arrays.asList(cus1, cus2);
    }

    @Autowired
    public void setPropagationService(PropagationService propagationService) {
        this.propagationService = propagationService;
    }
}
