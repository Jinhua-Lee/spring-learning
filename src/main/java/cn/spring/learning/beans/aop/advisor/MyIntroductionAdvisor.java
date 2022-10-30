package cn.spring.learning.beans.aop.advisor;

import cn.spring.learning.beans.aop.advice.MyIntroductionInterceptor;
import cn.spring.learning.beans.aop.bean.OriginFunction;
import cn.spring.learning.beans.aop.bean.TargetFunction;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Advisor可以理解为织入器
 * 可以拦截一批类型的obj，然后交给Advice去处理
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 10:42
 */
@Component
public class MyIntroductionAdvisor implements IntroductionAdvisor {

    @NonNull
    @Override
    public ClassFilter getClassFilter() {
        // isAssignableFrom 解读为：是xx父类
        // 哪些类要被增强
        return OriginFunction.class::isAssignableFrom;
    }

    @NonNull
    @Override
    public void validateInterfaces() throws IllegalArgumentException {
    }

    @NonNull
    @Override
    public Advice getAdvice() {
        // 模板方法提供通知，用于生成动态代理对象
        return new MyIntroductionInterceptor();
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @NonNull
    @Override
    public Class<?>[] getInterfaces() {
        return new Class<?>[]{TargetFunction.class};
    }
}
