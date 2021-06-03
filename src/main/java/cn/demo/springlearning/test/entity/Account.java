package cn.demo.springlearning.test.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 账户实体
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:40
 */
@Data
@Builder
public class Account {

    private Integer id;
    private String name;
    private Integer age;
    private BigDecimal balance = BigDecimal.ZERO;

    public Account(Integer id, String name, Integer age, BigDecimal balance) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.balance = balance;
    }

    public void setBalance(Double balance) {
        this.balance = BigDecimal.valueOf(balance);
    }
}
