package cn.spring.learning.conf;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/12/6 16:01
 */
@Slf4j
public class LogConfig extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException var3) {
            log.error("获取日志Ip异常", var3);
            return null;
        }
    }
}
