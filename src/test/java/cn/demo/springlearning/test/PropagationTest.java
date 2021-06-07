package cn.demo.springlearning.test;

import cn.demo.springlearning.test.entity.Commodity;
import cn.demo.springlearning.test.tx.PropagationService;
import org.junit.Test;

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

    /**
     * 1. Propagation_Required
     */
    @Test
    public void testRequired() {
        boolean b = propagationService.addCommodity(buildCommodities());

    }

    /**
     * 2. Propagation_Supports
     */
    @Test
    public void testSupports() {

    }

    /**
     * 3. Propagation_Mandatory
     */
    @Test
    public void testMandatory() {

    }

    /**
     * 4. Propagation_Requires_New
     */
    @Test
    public void testRequiresNew() {

    }

    /**
     * 5. Propagation_Not_Supported
     */
    @Test
    public void testNotSupported() {

    }

    /**
     * 6. Propagation_Never
     */
    @Test
    public void testNever() {

    }

    /**
     * 7. Propagation_Nested
     */
    @Test
    public void testNested() {

    }
}
