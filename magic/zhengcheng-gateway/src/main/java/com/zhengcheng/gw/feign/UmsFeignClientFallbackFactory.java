package com.zhengcheng.gw.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.zhengcheng.common.utils.Result;
import com.zhengcheng.gw.feign.dto.UserDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * UmsFeignClientFallbackFactory
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 14:24
 */
@Slf4j
@Component
public class UmsFeignClientFallbackFactory implements FallbackFactory<UmsFeignClient> {
    @Override
    public UmsFeignClient create(Throwable throwable) {
        return new UmsFeignClient() {
            @Override
            public Result<UserDTO> info(String token) {
                log.error("ums info fallback;reason was:{}", throwable.getMessage(), throwable);
                return Result.fallbackResult();
            }
        };
    }
}
