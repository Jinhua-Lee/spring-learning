package cn.demo.springlearning.task;

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

    @Scheduled(fixedRate = 2_000L)
    public void scheduledTask() {
        System.out.println("模拟scheduled定时任务..." + LocalDateTime.now());
    }
}
