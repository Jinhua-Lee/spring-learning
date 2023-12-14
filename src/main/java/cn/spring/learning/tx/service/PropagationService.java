package cn.spring.learning.tx.service;

import cn.spring.learning.tx.entity.Commodity;
import cn.spring.learning.tx.entity.Customer;

import java.util.List;

/**
 * @author Jinhua-Lee
 */
public interface PropagationService {

    /**
     * 添加商品
     *
     * @param commodities 商品
     * @return 添加结果
     */
    boolean addCommodities(List<Commodity> commodities);

    /**
     * 添加顾客
     *
     * @param customers 顾客
     * @return 添加结果
     */
    boolean addCustomers(List<Customer> customers);


    /**
     * 添加顾客，抛异常
     *
     * @param customers 顾客
     * @return 添加结果
     */
    boolean addCustomersException(List<Customer> customers);
}
