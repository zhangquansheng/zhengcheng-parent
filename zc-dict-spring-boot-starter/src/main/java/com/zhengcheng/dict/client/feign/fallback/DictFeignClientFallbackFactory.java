package com.zhengcheng.dict.client.feign.fallback;

import com.zhengcheng.common.web.Result;
import com.zhengcheng.dict.client.feign.DictFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * DictFeignClientFallbackFactory
 *
 * @author :    quansheng.zhang
 * @date :    2020/5/6 20:49
 */
@Slf4j
@Component
public class DictFeignClientFallbackFactory implements FallbackFactory<DictFeignClient> {
    @Override
    public DictFeignClient create(Throwable throwable) {
        return type -> {
            log.error("list,fallback;reason was:{}", throwable.getMessage(), throwable);
            return Result.fallbackResult();
        };
    }
}
