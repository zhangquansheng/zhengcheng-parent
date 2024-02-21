package com.zhengcheng.ums.controller;

import com.baomidou.kaptcha.Kaptcha;
import com.baomidou.kaptcha.dto.KaptchaDTO;
import com.zhengcheng.common.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Google Kaptcha验证码"})
@RestController
@RequestMapping("/kaptcha")
public class KaptchaController {

    @Autowired
    private Kaptcha kaptcha;

    @ApiOperation("获取验证码")
    @GetMapping("/render")
    public Result<KaptchaDTO> render() {
        return Result.successData(kaptcha.renderByRedis());
    }

}
