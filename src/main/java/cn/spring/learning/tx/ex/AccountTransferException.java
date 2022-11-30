package cn.spring.learning.tx.ex;

/**
 * 账户转账异常
 *
 * @author Jinhua
 * @version 1.0
 * @date 2022/11/30 19:41
 */
public class AccountTransferException extends RuntimeException {

    private static final String ERR_PREFIX = "[AccountTransfer] 账户转账操作异常. ";

    public AccountTransferException(String message) {
        super(ERR_PREFIX + message);
    }
}
