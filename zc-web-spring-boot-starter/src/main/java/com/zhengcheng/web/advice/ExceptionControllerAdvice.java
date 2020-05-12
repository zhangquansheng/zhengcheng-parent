package com.zhengcheng.web.advice;

import com.zhengcheng.common.web.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.common.exception.IdempotentException;
import com.zhengcheng.common.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.Objects;

/**
 * 统一异常处理
 *
 * @author :    quansheng.zhang
 * @date :    2019/2/28 21:00
 */
@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * IllegalArgumentException
     * 返回状态码:200
     */
    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result badRequestException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.BAD_REQUEST.getCode(), CodeEnum.BAD_REQUEST.getMessage(), e);
    }

    /**
     * HttpRequestMethodNotSupportedException
     * 返回状态码:200
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
//    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.create(CodeEnum.METHOD_NOT_ALLOWED.getCode(), CodeEnum.METHOD_NOT_ALLOWED.getMessage(), e);
    }

    /**
     * HttpMediaTypeNotSupportedException
     * 返回状态码:200
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.create(CodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), CodeEnum.UNSUPPORTED_MEDIA_TYPE.getMessage(), e);
    }


    /**
     * SQLException
     * 返回状态码:200
     */
    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleSQLException(SQLException e) {
        log.error("SQLException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), "服务运行SQLException异常", e);
    }

    /**
     * BusinessException 业务异常处理
     * 返回状态码:200
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(BizException e) {
        log.info("BizException:{}", e.getMessage());
        return Result.create(e.getCode(), e.getMessage());
    }

    /**
     * 所有异常统一处理
     * 返回状态码:200
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(Exception e) {
        log.error("Exception:{}", e.getMessage(), e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
    }

    /**
     * IdempotentException 幂等性异常
     * 返回状态码:200
     */
    @ExceptionHandler(IdempotentException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(IdempotentException e) {
        return Result.errorMessage(e.getMessage());
    }

    /**
     * BindException
     * 返回状态码:200
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
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

    /**
     * MethodArgumentNotValidException
     * 返回状态码:200
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
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