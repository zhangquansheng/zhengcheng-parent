package com.zhengcheng.ums.controller.admin.system;

import com.zhengcheng.common.domain.Result;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {

    /**
     * 生成验证码
     */
    @GetMapping(value = "/captchaImage", name = "生产验证码")
    public Result getCode(HttpServletResponse response) throws IOException {
        return Result.ok();
    }

}
