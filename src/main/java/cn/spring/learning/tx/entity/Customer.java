package cn.spring.learning.tx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class Customer implements Serializable {

    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
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
}
