package cn.spring.learning.scheduled.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Jinhua
 * @version 1.0
 * @date 19/04/2022 17:41
 */
@Slf4j
@Component
public class ScheduledBean {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduledRun() {
        log.info("scheduled run: now = {}", LocalDateTime.now());
    }
}
