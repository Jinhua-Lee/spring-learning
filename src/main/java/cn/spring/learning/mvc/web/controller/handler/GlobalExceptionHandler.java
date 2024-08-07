package cn.spring.learning.mvc.web.controller.handler;

import cn.spring.learning.common.ApiResult;
import cn.spring.learning.mvc.web.controller.MvcRestController;
import cn.spring.learning.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

/**
 * Controller层的全局异常处理器<p>&emsp;
 * - 对于javaEE而言，返回的不是界面，而是Rest数据，所以需要加上@ResponseBody注解
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/11 17:43
 */
@Slf4j
@RestControllerAdvice(basePackageClasses = MvcRestController.class)
public class GlobalExceptionHandler {

    /**
     * spring。validation校验异常
     *
     * @param ex      开发人员定义的参数校验规则，会抛出该异常
     * @return 响应结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String exMsg = ExceptionUtil.buildArgValidExceptMsg(ex);
        log.error(exMsg);
        return ApiResult.builder()
                .code(-1)
                .msg(exMsg == null ? ex.getMessage() : exMsg)
                .build();
    }

    /**
     * javax校验异常
     *
     * @param ex javax校验异常
     * @return 响应结果
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResult<Object> handleException(ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return new ApiResult<>(-2, ex.getMessage(), null);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ApiResult<String> handleException(HttpRequestMethodNotSupportedException ex) {
        String msg = "[不支持的请求类型] " + ex.getMethod();
        log.error(msg);
        return new ApiResult<>(-3, msg, null);
    }
}
