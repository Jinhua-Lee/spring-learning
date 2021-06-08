package cn.demo.springlearning.test.tx;

import cn.demo.springlearning.test.entity.Commodity;
import cn.demo.springlearning.test.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 事务传播行为测试的业务类<p>&emsp;
 * 修改两个业务方法的事务注解，测试七种传播行为
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:49
 */
@Service
public class PropagationService {

    private PropagationMapper propagationMapper;

    @Autowired
    public void setPropagationMapper(PropagationMapper propagationMapper) {
        this.propagationMapper = propagationMapper;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean addCommodities(List<Commodity> commodities) {
        if (CollectionUtils.isEmpty(commodities)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCommodities(commodities);
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean addCustomers(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCustomers(customers);
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public boolean addCustomersException(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCustomers(customers);
        System.out.println("insert = " + insert);
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        return insert > 0;
    }
}
