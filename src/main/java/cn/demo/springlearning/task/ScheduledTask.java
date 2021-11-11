package cn.demo.springlearning.task;

import cn.demo.springlearning.bean.SingletonBean;
import cn.demo.springlearning.source.MyApplicationContextAware;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * scheduling定时任务
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/7 11:07
 */
@Component
public class ScheduledTask {

    /**
     * 每2s执行一次
     */
    @Scheduled(fixedRate = 2_000L)
    public void simple() {
        System.out.println("模拟scheduled定时任务..." + LocalDateTime.now());
    }

    /**
     * 测试定时任务的阻塞
     * 每隔5s执行，开始秒数为0
     */
    @SneakyThrows
    @Scheduled(cron = "0/5 * * * * ?")
    public void testBlock() {
        System.out.println("start: " + LocalDateTime.now());
        Thread.sleep(10_000L);
        System.out.println("end: " + LocalDateTime.now());
        System.out.println("===========");
    }

    /**
     * 测试非阻塞
     */
    @SneakyThrows
    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void testNonBlock() {
        System.out.println("start: " + LocalDateTime.now());
        Thread.sleep(10_000L);
        System.out.println("end: " + LocalDateTime.now());
        System.out.println("===========");
    }

    /**
     * 测试Aware接口
     */
    @Scheduled(cron = "*/5 * * * * ?")
    public void testAware() {
        SingletonBean sBean = MyApplicationContextAware.getBean(SingletonBean.class);
        System.out.println(sBean);
    }

}
