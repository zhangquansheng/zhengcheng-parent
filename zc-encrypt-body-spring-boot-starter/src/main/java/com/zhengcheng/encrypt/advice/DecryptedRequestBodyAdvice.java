package com.zhengcheng.encrypt.advice;

import com.zhengcheng.encrypt.annotation.Decrypted;
import com.zhengcheng.encrypt.http.DecryptedHttpInputMessage;
import com.zhengcheng.encrypt.properties.EncryptBodyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 使用 @ControllerAdvice & RequestBodyAdvice 拦截 Controller方法默认入参解密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 13:54
 */
@SuppressWarnings("ALL")
@Slf4j
@Order(1)
@ControllerAdvice
public class DecryptedRequestBodyAdvice implements RequestBodyAdvice {

    private EncryptBodyProperties encryptBodyProperties;

    public DecryptedRequestBodyAdvice(EncryptBodyProperties encryptBodyProperties) {
        this.encryptBodyProperties = encryptBodyProperties;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(Decrypted.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            Method method = parameter.getMethod();
            if (method == null) {
                return null;
            }
            Decrypted decrypted = method.getAnnotation(Decrypted.class);
            return new DecryptedHttpInputMessage(inputMessage, encryptBodyProperties, decrypted);
        } catch (Exception e) {
            log.error("Decryption failed, message:[{}]", e.getMessage(), e);
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }


}
