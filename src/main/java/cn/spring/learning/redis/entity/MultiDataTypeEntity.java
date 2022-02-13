package cn.spring.learning.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/11 19:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MultiDataTypeEntity implements Serializable {

    private Long id;

    private String name;

    private LocalDateTime lastModifyTime;

    private BigDecimal balance;

    private Double rate;
}
