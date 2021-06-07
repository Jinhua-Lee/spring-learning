package cn.demo.springlearning.test.tx;

import cn.demo.springlearning.test.entity.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 事务传播行为测试的业务类
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:49
 */
@Service
public class PropagationService {

    private PropagationMapper propagationMapper;

    @Autowired
    public void setPropagationMapper(PropagationMapper propagationMapper) {
        this.propagationMapper = propagationMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean addCommodity(List<Commodity> commodities) {
        if (CollectionUtils.isEmpty(commodities)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        return propagationMapper.addCommodity(commodities) > 0;
    }
}
