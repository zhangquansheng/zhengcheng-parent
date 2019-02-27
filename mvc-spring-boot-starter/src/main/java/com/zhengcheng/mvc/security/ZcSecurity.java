package com.zhengcheng.mvc.security;

import com.zhengcheng.common.utils.HttpAesUtil;

/**
 * 通用安全工具类
 *
 * @author :    quansheng.zhang
 * @Filename :     ZcSecurity.java
 * @Package :     com.zhengcheng.mvc.security
 * @Description :
 * @date :    2019/2/1 0:11
 */
public class ZcSecurity {

    private String key;
    private String iv;

    public ZcSecurity(String key, String iv) {
        this.key = key;
        this.iv = iv;
    }

    /**
     * 对ID进行解密
     *
     * @param encryptId
     * @return
     */
    public String decryptId(String encryptId) {
        return HttpAesUtil.decrypt(encryptId, key, Boolean.FALSE, iv);
    }

}
