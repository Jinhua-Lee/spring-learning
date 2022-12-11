package cn.spring.learning.mvc.web.controller;

import cn.spring.learning.common.ApiResult;
import cn.spring.learning.mvc.dto.ValidParamDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/3/30 10:19
 */
@RestController
public class MvcDemoController {

    @PutMapping(value = "/hello")
    public ApiResult<String> hello() {
        return ApiResult.success("hello");
    }

    @GetMapping(value = "/test-filter-order")
    public boolean testFilterOrder() {
        return false;
    }

    @PostMapping(value = "/test-valid")
    public void testValidate(@RequestBody @Validated @NotNull ValidParamDTO validParam) {
    }
}
