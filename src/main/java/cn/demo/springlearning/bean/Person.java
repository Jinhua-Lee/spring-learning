package cn.demo.springlearning.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

/**
 * Person类Bean
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/24 21:17
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    private Integer id;
    private String name;

    @SneakyThrows
    @Override
    public String toString() {
        return new ObjectMapper().writeValueAsString(this);
    }
}
