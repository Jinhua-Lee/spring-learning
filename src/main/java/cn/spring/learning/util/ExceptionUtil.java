package cn.spring.learning.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/16 10:20
 */
public class ExceptionUtil {

    public static String buildArgValidExceptMsg(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            StringBuilder builder = new StringBuilder();
            allErrors.forEach(error -> builder
                    .append("[").append(error.getObjectName())
                    .append("] ").append(error.getDefaultMessage()).append(";")
            );
            return builder.toString();
        }
        return null;
    }
}
