package cn.spring.learning.tx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 用户类
 *
 * @author Jinhua
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User implements Serializable {

    /**
     * 用户Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户家乡
     */
    private String home;

    /**
     * 用户备注
     */
    private String info;

}
