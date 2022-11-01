package cn.spring.learning.tx.service;

import cn.spring.learning.tx.entity.Commodity;
import cn.spring.learning.tx.entity.Customer;
import cn.spring.learning.tx.mapper.CommodityMapper;
import cn.spring.learning.tx.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

    private CustomerMapper customerMapper;
    private CommodityMapper commodityMapper;

    @Transactional(propagation = Propagation.NESTED, rollbackFor = Exception.class)
    public boolean addCommodities(List<Commodity> commodities) {
        if (CollectionUtils.isEmpty(commodities)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        AtomicInteger insertAtomic = new AtomicInteger(0);
        commodities.forEach(commodity -> {
            int insert = this.commodityMapper.insert(commodity);
            insertAtomic.getAndAdd(insert);
        });
        int insert = insertAtomic.get();
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean addCustomers(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        AtomicInteger insertAtomic = new AtomicInteger(0);
        customers.forEach(customer -> {
            int insert = this.customerMapper.insert(customer);
            insertAtomic.getAndAdd(insert);
        });
        int insert = insertAtomic.get();
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean addCustomersException(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        AtomicInteger insertAtomic = new AtomicInteger(0);
        customers.forEach(customer -> {
            int insert = this.customerMapper.insert(customer);
            insertAtomic.getAndAdd(insert);
        });
        int insert = insertAtomic.get();
        System.out.println("insert = " + insert);
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        return insert > 0;
    }

    @Autowired
    public void setCustomerMapper(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Autowired
    public void setCommodityMapper(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }
}
