package cn.demo.springlearning.service;

import cn.demo.springlearning.mapper.TxDemoMapper;
import cn.demo.springlearning.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Spring事务管理模拟
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/3 21:16
 */
@Service
public class TxDemoService {

    private TxDemoMapper demoMapper;

    @Autowired
    public void setDemoMapper(TxDemoMapper demoMapper) {
        this.demoMapper = demoMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateBalance(Account account) throws IllegalArgumentException {
        if (Objects.isNull(account)
                || Objects.isNull(account.getName())
                || Objects.isNull(account.getAge())
                || Objects.isNull(account.getBalance())) {
            throw new IllegalArgumentException("不合法入参");
        }
        return demoMapper.upsertBalance(account) > 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void transfer(Account from, Account to, BigDecimal amount) {
        // 1. 转账金额入参校验
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("转账金额必须大于0");
        }
        // 2. from账户余额校验
        List<Account> accountDos = demoMapper.getBalanceById(from);
        if (CollectionUtils.isEmpty(accountDos) || accountDos.get(0).getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("账户不存在，或者账户余额不足！");
        }
        // 3. 开始转账逻辑
        from.setBalance(amount.negate().doubleValue());
        to.setBalance(amount.doubleValue());
        if (demoMapper.updateBalance(from) < 1) {
            throw new RuntimeException("from 账户更新失败！");
        }
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        if (demoMapper.updateBalance(to) < 1) {
            throw new RuntimeException("to 账户更新失败！");
        }
    }
}
