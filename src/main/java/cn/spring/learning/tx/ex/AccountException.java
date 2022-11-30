package cn.spring.learning.tx.ex;

/**
 * 账户异常
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/11/30 19:42
 */
public class AccountException extends RuntimeException {

    private static final String ERR_PREFIX = "[Account] 账户操作异常. ";

    public AccountException(String message) {
        super(ERR_PREFIX + message);
    }
}
