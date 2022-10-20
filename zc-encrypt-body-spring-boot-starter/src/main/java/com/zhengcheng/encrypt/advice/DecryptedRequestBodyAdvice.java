package com.zhengcheng.encrypt.advice;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import com.zhengcheng.encrypt.annotation.Decrypted;
import com.zhengcheng.encrypt.enums.DecryptBodyMethod;
import com.zhengcheng.encrypt.properties.EncryptBodyProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.stream.Collectors;

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
            return new EncryptedHttpInputMessage(inputMessage, encryptBodyProperties, decrypted);
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


    public class EncryptedHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;
        private InputStream body;

        public EncryptedHttpInputMessage(HttpInputMessage inputMessage, EncryptBodyProperties encryptBodyProperties, Decrypted decrypted) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines().collect(Collectors.joining(System.lineSeparator()));
            String decryptBody;
            if (content.startsWith("{")) {
                log.info("Unencrypted without decryption:[{}]", content);
                decryptBody = content;
            } else {
                StringBuilder json = new StringBuilder();
                content = content.replaceAll(" ", "+");

                if (!StringUtils.isEmpty(content)) {
                    String[] contents = content.split("\\|");
                    if (DecryptBodyMethod.RSA.equals(decrypted.value())) {
                        RSA rsa = SecureUtil.rsa(Base64.decodeStr(encryptBodyProperties.getRas().getPrivateKey()), Base64.decodeStr(encryptBodyProperties.getRas().getPublicKey()));
                        for (String value : contents) {
                            json.append(rsa.decryptStr(value, KeyType.PrivateKey));
                        }
                    } else if (DecryptBodyMethod.AES.equals(decrypted.value())) {
                        AES aes = SecureUtil.aes(encryptBodyProperties.getAes().getKey().getBytes());
                        for (String value : contents) {
                            json.append(aes.decryptStr(value));
                        }
                    }
                }
                decryptBody = json.toString();
            }
            this.body = new ByteArrayInputStream(decryptBody.getBytes());
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }
}
