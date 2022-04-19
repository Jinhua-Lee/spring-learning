package cn.spring.learning.scheduled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * @author Jinhua
 * @version 1.0
 * @date 19/04/2022 17:41
 */
@Configuration
@Slf4j
public class ScheduledBean {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduledPerQuarter() {
        log.info("scheduled run: now = {}", LocalDateTime.now());
    }
}
