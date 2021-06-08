package cn.demo.springlearning.test;

import cn.demo.springlearning.test.entity.Commodity;
import cn.demo.springlearning.test.entity.Customer;
import cn.demo.springlearning.test.tx.PropagationService;
import org.junit.Test;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 事务传播行为测试类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:31
 */
@Component
public class PropagationTest extends MyApplicationContext {

    private final PropagationService propagationService = (PropagationService) CONTEXT.getBean("propagationService");

    private List<Commodity> buildCommodities() {
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

    private List<Customer> buildCustomers() {
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

    /**
     * 1. Propagation_Required<p>
     * 当前没有事务，就创建一个新事务；当前存在事务，就加入该事务
     */
    @Test
    @Transactional
    public void testRequired() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，，两批数据都插入成功！（测试通过）
        // TODO: 2021/6/8 2. 该测试方法开启事务，两批都插入失败！（测试未通过）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 2. Propagation_Supports<p>
     * 当前存在事务，就加入该事务；当前不存在事务，就以非事务执行
     */
    @Test
    @Transactional
    public void testSupports() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，非事务执行，两批数据都插入成功！（测试通过）
        // 2. 该测试方法开启事务，加入事务，两批都插入失败！（测试通过）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 3. Propagation_Mandatory<p>
     * 当前存在事务，就加入该事务；当前不存在事务，就抛出异常，不执行
     */
    @Test
    public void testMandatory() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，不执行，两批数据都插入失败！（测试通过）
        // 直接抛出异常 org.springframework.transaction.IllegalTransactionStateException:
        // No existing transaction found for transaction marked with propagation 'mandatory'
        // 2. 该测试方法开启事务，加入事务
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 4. Propagation_Requires_New<p>
     * 无论当前是否存在事务，都创建新事务
     */
    @Test
    public void testRequiresNew() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，创建事务（不加入事务），两批数据都插入成功！（测试通过）
        // 2. 该测试方法开启事务，创建事务（不加入事务），两批都插入成功！（测试通过）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 5. Propagation_Not_Supported<p>
     * 非事务方式执行操作。当前存在事务，就把当前事务挂起
     */
    @Test
    @Transactional
    public void testNotSupported() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 无论当前方法是否开启事务，都以非事务方式执行，两批都插入成功！（测试通过）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 6. Propagation_Never
     */
    @Test
    @Transactional
    public void testNever() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，以非事务方式执行，两批数据都插入成功！（测试通过）
        // TODO: 2021/6/8 2. 该测试方法开启事务，不执行，抛出异常！（测试失败）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 7. Propagation_Nested
     */
    @Test
    public void testNested() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 1. 该测试方法未开启事务，以非事务方式执行，两批数据都插入成功！（测试通过）
        // TODO: 2021/6/8 2. 该测试方法开启事务，不执行，抛出异常！（测试失败）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }
}
