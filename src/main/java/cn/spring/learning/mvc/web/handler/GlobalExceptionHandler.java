package cn.spring.learning.mvc.web.handler;

import cn.spring.learning.common.ApiResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Controller层的全局异常处理器<p>&emsp;
 * - 对于javaEE而言，返回的不是界面，而是Rest数据，所以需要加上@ResponseBody注解
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/11 17:43
 */
@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * spring。validation校验异常
     *
     * @param request http请求
     * @param ex      开发人员定义的参数校验规则，会抛出该异常
     * @return 响应结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Object> handleMethodArgumentNotValid(HttpServletRequest request,
                                                          MethodArgumentNotValidException ex) {
        return new ApiResult<>(-1, ex.getMessage(), null);
    }

    /**
     * javax校验异常
     *
     * @param ex javax校验异常
     * @return 响应结果
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ApiResult<Object> handleException(ConstraintViolationException ex) {
        return new ApiResult<>(-2, ex.getMessage(), null);
    }
}
