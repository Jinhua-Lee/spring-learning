package cn.demo.springlearning.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * 通知（增加的方法）
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/29 20:48
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class MyAdvice {

    @Pointcut(value = "execution(* cn.demo.springlearning.bean.AopBean.method())")
    public void pc() {
    }

    /**
     * 前置通知 --> 目标方法调用之前进行调用
     */
    @Before(value = "pc()")
    public void before() {
        System.out.println("前置通知");
    }

    /**
     * 后置通知（出现异常不会调用）-->目标方法运行之后进行调用
     */
    @AfterReturning(value = "pc()")
    public void afterReturning() {
        System.out.println("后置通知");
    }

    /**
     * 环绕通知 --> 在目标方法之前和之后都调用
     *
     * @param pjp 手动配置对象
     * @return 返回对象
     * @throws Throwable 调用时可能发生的异常
     */
    @Around(value = "pc()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("环绕通知之前");
        // 调用目标方法的代码
        Object proceed = pjp.proceed();
        System.out.println("环绕通知之后");
        return proceed;
    }

    /**
     * 异常拦截通知 --> 如果出现异常，就会调用
     */
    @AfterThrowing(value = "pc()")
    public void afterThrowing() {
        System.out.println("异常出现");
    }

    /**
     * 后置通知（无论是否出现异常都会调用） --> 目标方法运行之后进行调用
     */
    @After(value = "pc()")
    public void after() {
        System.out.println("后置通知，无论异常是否出现都调用");
    }

}
