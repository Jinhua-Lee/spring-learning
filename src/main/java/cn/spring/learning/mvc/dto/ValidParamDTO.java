package cn.spring.learning.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/11 17:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class ValidParamDTO {

    @Min(value = 0,  message = "intVal不允许小于0")
    private Integer intVal;

    @NotBlank(message = "strVal不允许空串")
    private String strVal;
}
