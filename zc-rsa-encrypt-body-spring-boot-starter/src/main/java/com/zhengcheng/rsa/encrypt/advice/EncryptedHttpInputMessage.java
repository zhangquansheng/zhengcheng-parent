package com.zhengcheng.rsa.encrypt.advice;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.util.stream.Collectors;

/**
 * {@link com.zhangmen.brain.solar.rsa.encrypt.annotation.Encrypted} 的请求入参解密
 *
 * @author quansheng1.zhang
 * @since 2021/6/8 13:59
 */
@SuppressWarnings("ALL")
@Slf4j
public class EncryptedHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

    public EncryptedHttpInputMessage(HttpInputMessage inputMessage, RSA rsa) throws Exception {
        this.headers = inputMessage.getHeaders();
        String content = new BufferedReader(new InputStreamReader(inputMessage.getBody())).lines()
            .collect(Collectors.joining(System.lineSeparator()));
        String decryptBody;
        if (content.startsWith("{")) {
            log.info("Unencrypted without decryption:[{}]", content);
            decryptBody = content;
        } else {
            StringBuilder json = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StringUtils.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (String value : contents) {
                    json.append(rsa.decryptStr(value, KeyType.PrivateKey));
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
