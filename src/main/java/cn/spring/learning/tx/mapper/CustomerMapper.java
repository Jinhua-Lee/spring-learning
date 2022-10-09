package cn.spring.learning.tx.mapper;

import cn.spring.learning.tx.entity.Customer;
import cn.spring.learning.tx.mapper.provider.CustomerSqlProvider;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 客户访问接口
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/23 21:15
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

    /**
     * 获取所有客户
     *
     * @param namePart 姓名的部分
     * @param age      年龄
     * @return 客户列表
     */
    @SelectProvider(value = CustomerSqlProvider.class, method = "getCustomersByNameAndAge")
    List<Customer> getCustomersByNameAndAge(@Param("name") String namePart, @Param("age") Integer age);
}
