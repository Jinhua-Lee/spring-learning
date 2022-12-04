package cn.spring.learning.tx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDateTime;

/**
 * 业务方法计时的切面
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/4 17:01
 */
@Slf4j
@Aspect
@Component
public class ServiceMethodTimerAspect {

    @Around(value = "execution(public * cn.spring.learning.tx.service.*.*(..))")
    public Object methodExecLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String clazzName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        StopWatch watch = new StopWatch();
        watch.start();
        if (log.isDebugEnabled()) {
            log.debug("service method {} started at time = {}",
                    clazzName + "#" + methodName,
                    LocalDateTime.now()
            );
        }
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } finally {
            watch.stop();
            if (log.isDebugEnabled()) {
                log.debug("service method {} stopped, time cost = {} ms",
                        clazzName + "#" + methodName,
                        watch.getTotalTimeMillis()
                );
            }
        }
        return proceed;
    }
}
