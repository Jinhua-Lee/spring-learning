package cn.spring.learning.message.source;

import org.springframework.context.support.AbstractMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * 可以自定义消息源实现，bean名称固定
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/8/4 13:57
 */
@Component(value = "messageSource")
public class MyMessageSource extends AbstractMessageSource {

    @Override
    protected MessageFormat resolveCode(@NonNull String code, @NonNull Locale locale) {
        // 自己实现一些逻辑.
        return null;
    }
}
