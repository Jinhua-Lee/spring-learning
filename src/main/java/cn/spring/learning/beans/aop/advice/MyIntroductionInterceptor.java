package cn.spring.learning.beans.aop.advice;

import cn.spring.learning.beans.aop.bean.MyFunction;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 10:33
 */
@Slf4j
@Component
public class MyIntroductionInterceptor implements IntroductionInterceptor, MyFunction {
    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        log.info("I am IntroductionInterceptor");
        // 判断实现了目标类的接口，才执行当前类的方法调用
        if (implementsInterface(invocation.getMethod().getDeclaringClass())) {
            return invocation.getMethod().invoke(this, invocation.getArguments());
        }
        // 否则执行原始调用
        return invocation.proceed();
    }

    @Override
    public boolean implementsInterface(@NonNull Class<?> interfaceClass) {
        // isAssignableFrom别写反了，翻译为：是xx父类
        return MyFunction.class.isAssignableFrom(interfaceClass);
    }

    @Override
    public void doSomething() {
        log.info("拦截器做的一些事...");
    }
}
