package cn.demo.springlearning.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * 定时任务模拟类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/5/7 9:47
 */
public class DemoJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("模拟定时任务..." + LocalDateTime.now());
    }
}
