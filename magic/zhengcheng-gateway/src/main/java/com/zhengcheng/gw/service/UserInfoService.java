package com.zhengcheng.gw.service;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.zhengcheng.common.utils.Result;
import com.zhengcheng.gw.feign.UmsFeignClient;
import com.zhengcheng.gw.feign.dto.UserDTO;

/**
 * UserInfoService
 *
 * @author quansheng1.zhang
 * @since 2022/7/3 17:50
 */
@Service
public class UserInfoService {

    @Autowired
    private UmsFeignClient umsFeignClient;

    @Async
    public Future<UserDTO> getUserInfoByToken(String token) {
        Result<UserDTO> userResult = umsFeignClient.info(token);
        return new AsyncResult<>(userResult.getData());
    }
}
