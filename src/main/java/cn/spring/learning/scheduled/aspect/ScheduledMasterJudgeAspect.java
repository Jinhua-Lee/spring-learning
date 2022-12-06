package cn.spring.learning.scheduled.aspect;

import com.cet.electric.matterhorn.clusterassistant.util.ClusterAssistantManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ClusterAssistantManager clusterAssistant;

    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void pointCut() {
    }

    @Around(value = "pointCut()")
    public Object judgeMasterAndExecute(ProceedingJoinPoint joinPoint) throws Throwable {
        // 执行master的判断
        // 1. 若配置了${cet.base-service.cluster-assistant.enabled} 为false，则直接返回是master
        // 2. 若请求发送失败，默认是standby
        boolean master = clusterAssistant.isMaster();

        if (master) {
            if (log.isInfoEnabled()) {
                log.info("current machine is not master, we won't run this scheduled job.");
            }
            return Optional.empty();
        }
        return joinPoint.proceed();
    }

    @Autowired
    public void setClusterAssistant(ClusterAssistantManager clusterAssistant) {
        this.clusterAssistant = clusterAssistant;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
