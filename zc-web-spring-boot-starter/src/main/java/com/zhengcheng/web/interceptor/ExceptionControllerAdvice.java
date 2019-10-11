package com.zhengcheng.web.interceptor;

import com.zhengcheng.common.enumeration.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.exception.IdempotentException;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLException;
import java.util.Objects;

/**
 * 统一异常处理
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * IllegalArgumentException异常处理返回json
     * 返回状态码:400
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class})
    public Result badRequestException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:", e);
        return Result.create(CodeEnum.BAD_REQUEST.getCode(), CodeEnum.BAD_REQUEST.getMessage(), e);
    }

    /**
     * 返回状态码:405
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.create(CodeEnum.METHOD_NOT_ALLOWED.getCode(), CodeEnum.METHOD_NOT_ALLOWED.getMessage(), e);
    }

    /**
     * 返回状态码:415
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.create(CodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), CodeEnum.UNSUPPORTED_MEDIA_TYPE.getMessage(), e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(AccessDeniedException.class)
    public Result handleException(AccessDeniedException e) {
        log.info("AccessDeniedException:", e.getMessage());
        return Result.create(CodeEnum.FORBIDDEN.getCode(), CodeEnum.FORBIDDEN.getMessage());
    }

    /**
     * SQLException sql异常处理
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({SQLException.class})
    public Result handleSQLException(SQLException e) {
        log.error("SQLException:", e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), "服务运行SQLException异常", e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public Result handleException(BizException e) {
        log.info("BizException:", e.getMessage());
        return Result.create(e.getCode(), e.getMessage());
    }

    /**
     * 所有异常统一处理
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("Exception:{}", e.getMessage(), e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
    }

    /**
     * IdempotentException 幂等性异常
     * 返回状态码:200
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(IdempotentException.class)
    public Result handleException(IdempotentException e) {
        return Result.errorMessage(e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result bindExceptionHandler(BindException e) {
        String errorMsg = e.getAllErrors().stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return errorMessage(fieldError);
            }
            return objectError.getDefaultMessage();
        }).reduce((a, b) -> a + "; " + b).orElse("");
        return Result.errorMessage(errorMsg);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(objectError -> {
            if (objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                return errorMessage(fieldError);
            }
            return objectError.getDefaultMessage();
        }).reduce((a, b) -> a + "; " + b).orElse("");
        return Result.errorMessage(errorMsg);
    }

    private String errorMessage(FieldError fieldError) {
        String message = fieldError.getDefaultMessage();
        if (Objects.equals(message, "不能为空")) {
            message = fieldError.getField() + message;
        }
        return message;
    }
}