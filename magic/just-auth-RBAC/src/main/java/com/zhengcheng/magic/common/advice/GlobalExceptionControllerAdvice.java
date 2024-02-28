package com.zhengcheng.magic.common.advice;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zhengcheng.common.enums.CodeEnum;
import com.zhengcheng.common.domain.Result;
import com.zhengcheng.mvc.advice.ExceptionControllerAdvice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;

/**
 * GlobalExceptionControllerAdvice
 *
 * @author quansheng1.zhang
 * @since 2020/11/12 17:34
 */
@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalExceptionControllerAdvice extends ExceptionControllerAdvice {

    /**
     * NotLoginException 业务异常处理
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleNotLoginException(NotLoginException e) {
        log.warn("NotLoginException type:{},message:{}", e.getType(), e.getMessage());
        return Result.create(CodeEnum.UNAUTHORIZED.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleNotRoleException(NotRoleException e) {
        log.warn("无此角色：{}", e.getRole());
        return Result.create(CodeEnum.FORBIDDEN.getCode(), e.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<Void> handleNotPermissionException(NotPermissionException e) {
        log.warn("无此权限：{}", e.getCode());
        return Result.create(CodeEnum.FORBIDDEN.getCode(), e.getMessage());
    }

}
