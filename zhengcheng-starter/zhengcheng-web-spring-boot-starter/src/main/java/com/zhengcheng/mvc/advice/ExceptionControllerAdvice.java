package com.zhengcheng.mvc.advice;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.common.enums.CodeEnum;
import com.zhengcheng.common.exception.BizException;
import com.zhengcheng.mvc.utils.I18nUtil;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.sql.SQLException;
import java.util.Objects;

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
@Order()
public class ExceptionControllerAdvice {

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("MaxUploadSizeExceededException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getCode(), CodeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getMessage());
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleHttpMessageConversionException(HttpMessageConversionException e) {
        log.error("HttpMessageConversionException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.HTTP_MESSAGE_CONVERSION_EXCEPTION.getCode(), CodeEnum.HTTP_MESSAGE_CONVERSION_EXCEPTION.getMessage());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result badRequestException(IllegalArgumentException e) {
        log.error("IllegalArgumentException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.BAD_REQUEST.getCode(), e.getMessage());
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleServletRequestBindingException(ServletRequestBindingException e) {
        log.error("ServletRequestBindingException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.SERVLET_REQUEST_BINDING_EXCEPTION.getCode(), CodeEnum.SERVLET_REQUEST_BINDING_EXCEPTION.getMessage());
    }

    @ExceptionHandler({AsyncRequestTimeoutException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
        log.error("AsyncRequestTimeoutException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION.getCode(), CodeEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION.getMessage());
    }

    /**
     * HttpRequestMethodNotSupportedException
     * 返回状态码:200
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.create(CodeEnum.METHOD_NOT_ALLOWED.getCode(), CodeEnum.METHOD_NOT_ALLOWED.getMessage());
    }

    /**
     * HttpMediaTypeNotSupportedException
     * 返回状态码:200
     */
    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.create(CodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), CodeEnum.UNSUPPORTED_MEDIA_TYPE.getMessage());
    }


    /**
     * SQLException
     * 返回状态码:200
     */
    @ExceptionHandler({SQLException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result handleSQLException(SQLException e) {
        log.error("SQLException:{}", e.getMessage(), e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), "服务运行SQLException异常");
    }


    /**
     * BusinessException 业务异常处理
     * 返回状态码:200
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        return Result.create(e.getCode(), I18nUtil.message(e.getMessage(), e.getArgs()));
    }

    /**
     * 所有异常统一处理
     * 返回状态码:200
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Result handleException(Exception e) {
        log.error("Exception:{}", e.getMessage(), e);
        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage());
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
