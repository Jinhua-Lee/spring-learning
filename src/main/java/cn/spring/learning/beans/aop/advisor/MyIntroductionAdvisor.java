package cn.spring.learning.beans.aop.advisor;

import cn.spring.learning.beans.aop.advice.MyIntroductionInterceptor;
import cn.spring.learning.beans.aop.bean.MyFunction;
import org.aopalliance.aop.Advice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
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
        return MyFunction.class::isAssignableFrom;
    }

    @NonNull
    @Override
    public void validateInterfaces() throws IllegalArgumentException {

    }

    @NonNull
    @Override
    public Advice getAdvice() {
        return new MyIntroductionInterceptor();
    }

    @Override
    public boolean isPerInstance() {
        return true;
    }

    @NonNull
    @Override
    public Class<?>[] getInterfaces() {
        return new Class<?>[]{MyFunction.class};
    }
}
