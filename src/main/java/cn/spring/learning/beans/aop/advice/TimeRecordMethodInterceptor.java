package cn.spring.learning.beans.aop.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.lang.NonNull;

import java.time.Instant;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/24 19:54
 */
@Slf4j
public class TimeRecordMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        long start = Instant.now().toEpochMilli();
        invocation.proceed();
        long end = Instant.now().toEpochMilli();

        log.debug("方法 {} 运行耗时：{} ms", invocation.getMethod().getName(), end - start);
        return null;
    }
}
