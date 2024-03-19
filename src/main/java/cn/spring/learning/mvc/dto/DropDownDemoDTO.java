package cn.spring.learning.mvc.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Jinhua-Lee
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropDownDemoDTO {

    @ExcelProperty(value = "ID", index = 0)
    private Long id;
    @ExcelProperty(value = "姓名", index = 1)
    private String name;
    @ExcelProperty(value = "性别", index = 2)
    private String gender;
}
