package cn.spring.learning.beans.aop.pointcut;


import cn.spring.learning.beans.aop.annotation.TimeRecord;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

/**
 * 【运行时间统计】切点定义：<p>&emsp;
 * 1. 方法上有TimeRecord注解，表示匹配成功
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/24 20:03
 */
public class TimeRecordPointcut implements Pointcut {

    @NonNull
    @Override
    public ClassFilter getClassFilter() {
        return ClassFilter.TRUE;
    }

    @NonNull
    @Override
    public MethodMatcher getMethodMatcher() {
        return new MethodMatcher() {
            @Override
            public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass) {
                // 方法上有TimeRecord注解时候，匹配成功
                return this.matches(method, targetClass, new Object[1]);
            }

            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(@NonNull Method method, @NonNull Class<?> targetClass, @Nullable Object... args) {
                return method.isAnnotationPresent(TimeRecord.class);
            }
        };
    }
}
