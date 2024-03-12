package com.zhengcheng.satoken.advice;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.common.enums.CodeEnum;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cn.dev33.satoken.exception.NotPermissionException;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一异常处理
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Slf4j
@ConditionalOnProperty(value = "zc.exception-controller-advice.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@ConditionalOnWebApplication
@RestControllerAdvice
public class SaTokenExceptionControllerAdvice {

    @ExceptionHandler(NotPermissionException.class)
    public Result handleNotPermissionException(NotPermissionException e) {
        return Result.create(CodeEnum.FORBIDDEN.getCode(), e.getMessage());
    }

}
