package com.zhengcheng.ums.controller;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.zhengcheng.ums.dto.RsaKeyPair;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zhengcheng.common.web.Result;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * EncryptorController
 *
 * @author quansheng1.zhang
 * @since 2022/5/13 15:58
 */
@Api(tags = { "加密服务接口" })
@RestController
@RequestMapping("/crypto")
public class CryptoController {

    @ApiOperation("MD5加密")
    @PostMapping("/md5")
    public Result<String> md5(@RequestParam("plaintext") String plaintext, HttpServletRequest request) {
        Enumeration<String> mode = request.getHeaderNames();

        while (mode.hasMoreElements()) {
            String key = mode.nextElement();
            System.out.println(key + "--->" + Arrays.toString(new String[] { request.getHeader(key) }));
        }
        return Result.successData(SecureUtil.md5(plaintext));
    }

    @ApiOperation("jasypt加密")
    @PostMapping("/jasypt")
    public Result<String> jasypt(@RequestParam("password") String password,
                                 @RequestParam("plaintext") String plaintext) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(password);
        return Result.successData(textEncryptor.encrypt(plaintext));
    }

    @ApiOperation("RSA生成密钥对")
    @PostMapping("/ras/keyPair")
    public Result<RsaKeyPair> rasKeyPair() {
        RSA rsa = new RSA();
        return Result.successData(RsaKeyPair.builder().privateKeyBase64(rsa.getPrivateKeyBase64())
                .publicKeyBase64(rsa.getPublicKeyBase64()).build());
    }

    @ApiOperation("RSA公钥加密,加密后使用Base64.encode方法转为Base64，便于存储为文本")
    @PostMapping("/ras/encrypt")
    public Result<String> rasEncrypt(@RequestParam("publicKey") String publicKey,
                                     @RequestParam("plaintext") String plaintext) {
        RSA rsa = new RSA(null, publicKey);
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(plaintext, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return Result.successData(Base64.encode(encrypt));
    }

    @ApiOperation("RSA私钥解密")
    @PostMapping("/ras/decrypt")
    public Result<String> rasDecrypt(@RequestParam("privateKey") String privateKey,
                                     @RequestParam("ciphertextBase64") String ciphertextBase64) {
        RSA rsa = new RSA(privateKey, null);
        byte[] decrypt = rsa.decrypt(Base64.decode(ciphertextBase64), KeyType.PrivateKey);
        return Result.successData(new String(decrypt));
    }

}
