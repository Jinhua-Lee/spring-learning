package cn.spring.learning.tx.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电商客户
 *
 * @author Jinhua
 * @date 2020/12/26 0:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户性别
     */
    private String gender;

    /**
     * 客户年龄
     */
    private Integer age;

    /**
     * 除去唯一主键的构造方法
     *
     * @param name   姓名
     * @param gender 性别
     * @param age    年龄
     */
    public Customer(String name, String gender, Integer age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
