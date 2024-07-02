package cn.spring.learning.redis.controller;

import cn.spring.learning.common.ApiResult;
import cn.spring.learning.redis.entity.MultiDataTypeEntity;
import cn.spring.learning.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jinhua
 * @version 1.0
 * @date 2022/2/11 19:27
 */
@Slf4j
@RestController
@RequestMapping(value = "/redis")
@SuppressWarnings(value = "all")
public class TestRedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping(value = "/simpleTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<MultiDataTypeEntity> simpleTest() {
        List<MultiDataTypeEntity> mDataTypes = multiDataTypes();
        MultiDataTypeEntity multiDataType = mDataTypes.get(0);
        String key = "redis:simple:" + multiDataType.getId();
        redisService.set(key, multiDataType);
        MultiDataTypeEntity cacheMultiDataType = (MultiDataTypeEntity) redisService.get(key);
        return ApiResult.success(cacheMultiDataType);
    }

    private List<MultiDataTypeEntity> multiDataTypes() {
        List<MultiDataTypeEntity> mDataTypes = new ArrayList<>();
        mDataTypes.add(MultiDataTypeEntity.builder()
                .id(1L)
                .name("One")
                .rate(0.1d)
                .balance(BigDecimal.valueOf(0.618d))
                .lastModifyTime(
                        LocalDateTime.of(
                                LocalDate.of(2019, 1, 1),
                                LocalTime.of(1, 1)
                        )
                )
                .build()
        );

        return mDataTypes;
    }

    @GetMapping(value = "/atomic")
    public ApiResult<Long> testRedisAtomic() {

        class MyRuntimeException extends RuntimeException {
            public MyRuntimeException() {
                super();
            }

            public MyRuntimeException(String message) {
                super(message);
            }
        }

        // 为null时会报空指针
        RedisAtomicLong atomicLong = redisService.getRedisAtomicLong("redis:atomic", null);
        try {
            atomicLong.getAndAdd(2);
        } catch (Exception e) {
            log.error("{}", e);
            throw new MyRuntimeException(e.getMessage());
        }
        return ApiResult.success(atomicLong.get());
    }
}
