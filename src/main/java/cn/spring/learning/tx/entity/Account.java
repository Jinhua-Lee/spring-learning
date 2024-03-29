package cn.spring.learning.tx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账户实体
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 23:40
 */
@Data
@NoArgsConstructor
@Builder
public class Account implements Serializable {

    @TableId(type = IdType.AUTO)
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
