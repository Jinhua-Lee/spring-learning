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
public class MvcRestController {

    @PutMapping(value = "/hello")
    public ApiResult<String> hello() {
        return ApiResult.success("hello");
    }

    @GetMapping(value = "/test-filter-order")
    public ApiResult<Boolean> testFilterOrder() {
        return ApiResult.success(false);
    }

    @PostMapping(value = "/test-valid")
    public ApiResult<Object> testValidate(@RequestBody @Validated @NotNull ValidParamDTO validParam) {
        return ApiResult.success(validParam);
    }
}
