package com.zhengcheng.ums.service;

import com.zhengcheng.ums.BaseTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * SysPasswordService
 *
 * @author quansheng1.zhang
 * @since 2024/3/8 21:09
 */
@Slf4j
public class SysPasswordServiceTest extends BaseTest {

    @Autowired
    private SysPasswordService sysPasswordService;

    @Test
    public void encode() {
        log.info(sysPasswordService.encode("zqs@1212"));
    }
}
