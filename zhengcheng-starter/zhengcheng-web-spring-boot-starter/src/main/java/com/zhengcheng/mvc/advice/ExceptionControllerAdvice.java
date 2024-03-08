package com.zhengcheng.mvc.advice;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.common.exception.BizException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
public class ExceptionControllerAdvice {

//    @ExceptionHandler({MaxUploadSizeExceededException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<Void> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
//        log.error("MaxUploadSizeExceededException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getCode(), CodeEnum.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION.getMessage());
//    }
//
//    @ExceptionHandler({HttpMessageConversionException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<Void> handleHttpMessageConversionException(HttpMessageConversionException e) {
//        log.error("HttpMessageConversionException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.HTTP_MESSAGE_CONVERSION_EXCEPTION.getCode(), CodeEnum.HTTP_MESSAGE_CONVERSION_EXCEPTION.getMessage());
//    }
//
//    @ExceptionHandler({IllegalArgumentException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<Void> badRequestException(IllegalArgumentException e) {
//        log.error("IllegalArgumentException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.BAD_REQUEST.getCode(), e.getMessage());
//    }
//
//    @ExceptionHandler({ServletRequestBindingException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<Void> handleServletRequestBindingException(ServletRequestBindingException e) {
//        log.error("ServletRequestBindingException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.SERVLET_REQUEST_BINDING_EXCEPTION.getCode(), CodeEnum.SERVLET_REQUEST_BINDING_EXCEPTION.getMessage());
//    }
//
//    @ExceptionHandler({AsyncRequestTimeoutException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<Void> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException e) {
//        log.error("AsyncRequestTimeoutException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION.getCode(), CodeEnum.ASYNC_REQUEST_TIMEOUT_EXCEPTION.getMessage());
//    }
//
//    /**
//     * HttpRequestMethodNotSupportedException
//     * 返回状态码:200
//     */
//    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<HttpRequestMethodNotSupportedException> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
//        return Result.create(CodeEnum.METHOD_NOT_ALLOWED.getCode(), CodeEnum.METHOD_NOT_ALLOWED.getMessage(), e);
//    }
//
//    /**
//     * HttpMediaTypeNotSupportedException
//     * 返回状态码:200
//     */
//    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<HttpMediaTypeNotSupportedException> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
//        return Result.create(CodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode(), CodeEnum.UNSUPPORTED_MEDIA_TYPE.getMessage(), e);
//    }
//
//
//    /**
//     * SQLException
//     * 返回状态码:200
//     */
//    @ExceptionHandler({SQLException.class})
//    @ResponseStatus(HttpStatus.OK)
//    public Result<SQLException> handleSQLException(SQLException e) {
//        log.error("SQLException:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), "服务运行SQLException异常", e);
//    }
//

    /**
     * BusinessException 业务异常处理
     * 返回状态码:200
     */
    @ExceptionHandler(BizException.class)
    public Result handleBizException(BizException e) {
        log.warn("BizException:{}", e.getMessage());
        return Result.error(e.getMessage());
    }
//
//    /**
//     * 所有异常统一处理
//     * 返回状态码:200
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.OK)
//    public Result<String> handleException(Exception e) {
//        log.error("Exception:{}", e.getMessage(), e);
//        return Result.create(CodeEnum.INTERNAL_SERVER_ERROR.getCode(), CodeEnum.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage());
//    }
//
//    /**
//     * BindException
//     * 返回状态码:200
//     */
//    @ExceptionHandler(BindException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public Result<String> bindExceptionHandler(BindException e) {
//        String errorMsg = e.getAllErrors().stream().map(objectError -> {
//            if (objectError instanceof FieldError) {
//                FieldError fieldError = (FieldError) objectError;
//                return errorMessage(fieldError);
//            }
//            return objectError.getDefaultMessage();
//        }).reduce((a, b) -> a + "; " + b).orElse("");
//        return Result.errorMessage(errorMsg);
//    }
//
//    /**
//     * MethodArgumentNotValidException
//     * 返回状态码:200
//     */
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(HttpStatus.OK)
//    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
//        String errorMsg = e.getBindingResult().getAllErrors().stream().map(objectError -> {
//            if (objectError instanceof FieldError) {
//                FieldError fieldError = (FieldError) objectError;
//                return errorMessage(fieldError);
//            }
//            return objectError.getDefaultMessage();
//        }).reduce((a, b) -> a + "; " + b).orElse("");
//        return Result.errorMessage(errorMsg);
//    }
//
//    private String errorMessage(FieldError fieldError) {
//        String message = fieldError.getDefaultMessage();
//        if (Objects.equals(message, "不能为空")) {
//            message = fieldError.getField() + message;
//        }
//        return message;
//    }
}
