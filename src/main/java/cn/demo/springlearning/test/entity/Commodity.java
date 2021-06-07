package cn.demo.springlearning.test.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品实体类
 *
 * @author Jinhua
 * @date 2020/12/26 0:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Commodity {

    /**
     * 自增主键
     */
    private Integer id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品单价
     */
    private BigDecimal price;

    /**
     * 商品产地
     */
    private String produceCity;
}