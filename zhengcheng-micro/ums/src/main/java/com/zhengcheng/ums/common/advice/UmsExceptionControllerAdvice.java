package com.zhengcheng.ums.common.advice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import com.baomidou.kaptcha.exception.KaptchaException;
import com.baomidou.kaptcha.exception.KaptchaIncorrectException;
import com.baomidou.kaptcha.exception.KaptchaNotFoundException;
import com.baomidou.kaptcha.exception.KaptchaTimeoutException;
import com.zhengcheng.common.web.Result;
import com.zhengcheng.core.advice.ExceptionControllerAdvice;
import com.zhengcheng.ums.common.exception.UmsError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * AdminExceptionControllerAdvice
 *
 * @author quansheng1.zhang
 * @since 2022/4/21 23:59
 */
@Slf4j
@Configuration
@RestControllerAdvice
public class UmsExceptionControllerAdvice extends ExceptionControllerAdvice {

    @ExceptionHandler({NotPermissionException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleMaxUploadSizeExceededException(NotPermissionException e) {
        log.warn("NotPermissionException code :{} , message:{} ", e.getCode(), e.getMessage());
        return Result.create(UmsError.NOT_PERMISSION_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler({NotLoginException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleMaxUploadSizeExceededException(NotLoginException e) {
        log.warn("NotLoginException code :{} , message:{} ", e.getCode(), e.getMessage());
        return Result.create(UmsError.NOT_LOGIN_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = KaptchaException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> kaptchaExceptionHandler(KaptchaException kaptchaException) {
        if (kaptchaException instanceof KaptchaIncorrectException) {
            return Result.create(UmsError.KAPTCHA_EXCEPTION.getCode(), "验证码不正确");
        } else if (kaptchaException instanceof KaptchaNotFoundException) {
            return Result.create(UmsError.KAPTCHA_EXCEPTION.getCode(), "验证码未找到");
        } else if (kaptchaException instanceof KaptchaTimeoutException) {
            return Result.create(UmsError.KAPTCHA_EXCEPTION.getCode(), "验证码过期");
        } else {
            return Result.create(UmsError.KAPTCHA_EXCEPTION.getCode(), "验证码渲染失败");
        }
    }

}
