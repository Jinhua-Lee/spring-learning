package cn.demo.springlearning.test.propagation;

import cn.demo.springlearning.test.PropagationTest;
import org.junit.Test;

/**
 * 4. 测试RequiresNew：<p>&emsp;
 * 一律创建新事务。
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/8 15:49
 */
public class RequiresNewTest extends PropagationTest {

    /**
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
}
