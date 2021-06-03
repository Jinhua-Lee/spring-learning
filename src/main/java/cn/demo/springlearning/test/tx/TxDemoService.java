package cn.demo.springlearning.test.tx;

import cn.demo.springlearning.test.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private TxDemoMapper demoDao;

    @Autowired
    public void setDemoDao(TxDemoMapper demoDao) {
        this.demoDao = demoDao;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateBalance(Account account) throws IllegalArgumentException {
        if (Objects.isNull(account)
                || Objects.isNull(account.getName())
                || Objects.isNull(account.getAge())
                || Objects.isNull(account.getBalance())) {
            throw new IllegalArgumentException("不合法入参");
        }
        return demoDao.upsertBalance(account) > 0;
    }
}
