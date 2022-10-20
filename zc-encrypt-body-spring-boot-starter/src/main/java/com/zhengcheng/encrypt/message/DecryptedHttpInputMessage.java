package com.zhengcheng.encrypt.message;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.*;
import java.util.stream.Collectors;

/**
 * DecryptedHttpInputMessage 解密信息输入流
 *
 * @author quansheng1.zhang
 * @since 2022/10/20 13:44
 */
@Slf4j
public class DecryptedHttpInputMessage implements HttpInputMessage {
    private HttpHeaders headers;
    private InputStream body;

    public DecryptedHttpInputMessage(HttpInputMessage inputMessage, EncryptBodyProperties encryptBodyProperties, Decrypted decrypted) throws Exception {
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
