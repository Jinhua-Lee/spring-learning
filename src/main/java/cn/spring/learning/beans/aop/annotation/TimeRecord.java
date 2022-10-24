package cn.spring.learning.beans.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运行时长统计的注解
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/24 19:53
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface TimeRecord {
}
