package com.zhengcheng.encrypt.advice;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhengcheng.encrypt.enums.EncryptBodyMethod;
import com.zhengcheng.encrypt.properties.EncryptBodyProperties;
import com.zhengcheng.encrypt.annotation.Encrypted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * 使用 @ControllerAdvice & ResponseBodyAdvice 拦截 Controller方法默认返回参数，统一进行 RSA 加密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 10:13
 */
@SuppressWarnings("ALL")
@Slf4j
@ControllerAdvice
public class EncryptedResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private ObjectMapper objectMapper;

    private EncryptBodyProperties encryptBodyProperties;

    public EncryptedResponseBodyAdvice(ObjectMapper objectMapper, EncryptBodyProperties encryptBodyProperties) {
        this.objectMapper = objectMapper;
        this.encryptBodyProperties = encryptBodyProperties;
    }

    /**
     * 判断是否要执行 beforeBodyWrite方法，true为执行，false不执行
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Objects.requireNonNull(returnType.getMethod()).isAnnotationPresent(Encrypted.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return body;
        }

        try {
            Method method = returnType.getMethod();
            if (method != null) {
                Encrypted encrypted = method.getAnnotation(Encrypted.class);
                String bodyJsonStr = objectMapper.writeValueAsString(body);

                if (EncryptBodyMethod.RSA.equals(encrypted.value())) {
                    RSA rsa = SecureUtil.rsa(Base64.decodeStr(encryptBodyProperties.getRas().getPrivateKey()), Base64.decodeStr(encryptBodyProperties.getRas().getPublicKey()));
                    return Base64.encode(rsa.encrypt(bodyJsonStr, StandardCharsets.UTF_8, KeyType.PublicKey));
                } else if (EncryptBodyMethod.AES.equals(encrypted.value())) {
                    return SecureUtil.aes(encryptBodyProperties.getAes().getKey().getBytes()).encryptHex(bodyJsonStr);
                }
            }
        } catch (Exception e) {
            log.error("Encrypted data exception, message:[{}]", e.getMessage(), e);
        }
        return null;
    }

}
