package cn.demo.springlearning.config;

import cn.demo.springlearning.task.DemoJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置类
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/7 10:01
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail testJobDetail() {
        return JobBuilder.newJob(DemoJob.class).withIdentity("demoJob").storeDurably().build();
    }

    @Bean
    public Trigger testTrigger() {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(2)
                .repeatForever();
        // 构建Trigger实例，每10s执行一次
        return TriggerBuilder.newTrigger()
                .forJob(testJobDetail())
                .withIdentity("demoJob")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
