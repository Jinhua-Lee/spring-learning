package cn.demo.springlearning.test.tx;

import cn.demo.springlearning.test.entity.Commodity;
import cn.demo.springlearning.test.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 测试事务传播行为数据访问
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:50
 */
@Mapper
public interface PropagationMapper {

    /**
     * 添加商品的方法
     *
     * @param commodities 商品实体列表
     * @return 受影响的行数
     */
    int addCommodities(List<Commodity> commodities);

    /**
     * 添加顾客的方法
     *
     * @param customers 顾客
     * @return 受影响的行数
     */
    int addCustomers(List<Customer> customers);
}
