package cn.spring.learning.scheduled.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * 定时任务执行的主备判断切面
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/6 14:54
 */
@Slf4j
@Aspect
@Component
public class ScheduledMasterJudgeAspect implements PriorityOrdered {

    @Value("${learning.scheduling.master:true}")
    private boolean master;

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object judgeMasterAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行master的判断
        // 若配置了${cet.base-service.cluster-assistant.enabled}
        //     1) 为false，则直接返回是master
        //     2) true，则默认要发送请求
        //          - 若请求发送失败，默认是standby
        //          - 拿请求结果
        if (!master) {
            if (log.isDebugEnabled()) {
                log.debug("current machine is not master, we won't run this scheduled job.");
            }
            return Optional.empty();
        }
        return joinPoint.proceed();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
