package cn.spring.learning.beans.aop.advisor;

import cn.spring.learning.beans.aop.advice.TimeRecordMethodInterceptor;
import cn.spring.learning.beans.aop.pointcut.TimeRecordPointcut;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 织入器构建
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/24 20:09
 */
@Component
public class TimeRecordAdvisor implements PointcutAdvisor {

    private final TimeRecordMethodInterceptor advice = new TimeRecordMethodInterceptor();
    private final TimeRecordPointcut pointcut = new TimeRecordPointcut();

    @NonNull
    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @NonNull
    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public boolean isPerInstance() {
        return false;
    }
}
