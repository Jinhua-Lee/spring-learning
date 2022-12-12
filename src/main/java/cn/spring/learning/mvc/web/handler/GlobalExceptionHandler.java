package cn.spring.learning.mvc.web.handler;

import cn.spring.learning.common.ApiResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * Controller层的全局异常处理器<p>&emsp;
 * - 对于javaEE而言，返回的不是界面，而是Rest数据，所以需要加上@ResponseBody注解
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/11 17:43
 */
@RestControllerAdvice()
public class GlobalExceptionHandler {

    /**
     * spring。validation校验异常
     *
     * @param ex      开发人员定义的参数校验规则，会抛出该异常
     * @return 响应结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            StringBuilder builder = new StringBuilder();
            allErrors.forEach(error -> builder
                    .append("[").append(error.getObjectName())
                    .append("] ").append(error.getDefaultMessage()).append(";")
            );
            return new ApiResult<>(-1, builder.toString(), null);
        }

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

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ApiResult<String> handleException(HttpRequestMethodNotSupportedException ex) {
        return new ApiResult<>(-3, "[不支持的请求类型] " + ex.getMethod(), null);
    }
}