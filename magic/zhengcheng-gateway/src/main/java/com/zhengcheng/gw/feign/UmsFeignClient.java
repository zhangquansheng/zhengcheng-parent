package com.zhengcheng.gw.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhengcheng.common.utils.Result;
import com.zhengcheng.gw.feign.dto.UserDTO;

/**
 * UmsFeignClient
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 14:21
 */
@FeignClient(name = "ums", fallbackFactory = UmsFeignClientFallbackFactory.class)
public interface UmsFeignClient {

    /**
     * 根据token获取用户基本信息
     */
    @GetMapping("/user/info")
    Result<UserDTO> info(@RequestParam("satoken") String token);
}
