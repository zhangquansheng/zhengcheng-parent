package com.zhengcheng.rsa.encrypt.advice;

import cn.hutool.crypto.asymmetric.RSA;
import com.zhengcheng.rsa.encrypt.EncryptedAutoConfiguration;
import com.zhengcheng.rsa.encrypt.annotation.Encrypted;
import com.zhengcheng.rsa.encrypt.properties.RsaEncryptProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * 使用 @ControllerAdvice & RequestBodyAdvice 拦截 Controller方法默认入数，统一进行 RSA 解密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 13:54
 */
@SuppressWarnings("ALL")
@Slf4j
@ControllerAdvice
public class EncryptedRequestBodyAdvice implements RequestBodyAdvice {

    @Autowired
    private RsaEncryptProperties rsaEncryptProperties;
    @Qualifier(EncryptedAutoConfiguration.RSA_BEAN_NAME)
    @Autowired
    private RSA rsa;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(Encrypted.class)
                && rsaEncryptProperties.isEnabled();
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        try {
            return new EncryptedHttpInputMessage(inputMessage, rsa);
        } catch (Exception e) {
            log.error("Decryption failed, message:[{}]", e.getMessage(), e);
        }
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
