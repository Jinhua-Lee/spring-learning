package cn.spring.learning.beans.aop.advice;

import cn.spring.learning.beans.aop.bean.TargetFunction;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 测试IntroductionAdvisor的拦截器<p>&emsp;
 * 1) 要调用目标接口的方法，就得实现目标接口TargetFunction.<p>&emsp;
 * 2) 要实现拦截器逻辑才能完成拦截操作.<p>&emsp;
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/10/25 10:33
 */
@Slf4j
@Component
public class MyIntroductionInterceptor implements IntroductionInterceptor, TargetFunction {
    @Override
    public Object invoke(@NonNull MethodInvocation invocation) throws Throwable {
        // 判断实现了目标类的接口，才执行当前类的方法调用
        if (implementsInterface(invocation.getMethod().getDeclaringClass())) {
            log.info("I am IntroductionInterceptor");
            return invocation.getMethod().invoke(this, invocation.getArguments());
        }
        // 否则执行原始调用
        return invocation.proceed();
    }

    @Override
    public boolean implementsInterface(@NonNull Class<?> interfaceClass) {
        // isAssignableFrom别写反了，翻译为：是xx父类
        // 当前类实现了目标接口
        return interfaceClass.isAssignableFrom(this.getClass());
    }

    @Override
    public void doTarget() {
        // 似乎只有这种写法？interceptor实现类和目标接口形成继承关系，无法解开耦合吗？

        log.info("拦截器实现了，target的方法...");
    }
}
