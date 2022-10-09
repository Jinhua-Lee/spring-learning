package cn.spring.learning.tx.mapper;

import cn.spring.learning.tx.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/9/15 10:03
 */
@Mapper
public interface CommodityMapper extends BaseMapper<Commodity> {
}
