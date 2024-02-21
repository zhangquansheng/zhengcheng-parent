package com.zhengcheng.ums.service;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import com.zhengcheng.ums.controller.BaseTest;

/**
 * BasicTextEncryptorTest
 *
 * @author quansheng1.zhang
 * @since 2022/7/9 22:34
 */
public class BasicTextEncryptorTest extends BaseTest {

    @Test
    public void encrypt() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword("123456");
        System.out.println(textEncryptor.encrypt("zqs@123456"));
        System.out.println(textEncryptor.encrypt("Zhanshu829**"));
    }
}
