package cn.spring.learning.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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

    @Min(value = 0,  message = "不允许小于0")
    private Integer intVal;

    @NotBlank(message = "不允许空串")
    private String strVal;
}
