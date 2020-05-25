package com.zhengcheng.common.util;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.zhengcheng.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 签名验证工具类
 *
 * @author :    zhangquansheng
 * @date :    2020/5/7 18:54
 */
@Slf4j
public class SignAuthUtils {

    /**
     * 按照字顺序进行升序排序
     * 其中value 需要URLEncoder
     *
     * @param params 请求参数
     * @return 排序后结果
     */
    public static String sortQueryParamString(Map<String, Object> params) {
        List<String> listKeys = Lists.newArrayList(params.keySet());
        Collections.sort(listKeys);
        StrBuilder content = StrBuilder.create();
        for (String key : listKeys) {
            try {
                String value = URLEncoder.encode(params.get(key).toString(), CommonConstants.UTF_8);
                // URL中关于空格的编码转换成+或转换成%20的问题 https://www.jianshu.com/p/4a7eb969235d
                value = value.replaceAll("\\+", "%20");
                content.append(key).append("=").append(value).append("&");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
        }
        if (content.length() > 0) {
            return content.subString(0, content.length() - 1);
        }
        return content.toString();
    }

    /**
     * 获取MD5的签名，全部转换成小写
     *
     * @param qs        按照字顺序进行升序排序字符串
     * @param timestamp 时间戳
     * @param nonceStr  随机字符串
     * @param key       秘钥
     * @return Md5的签名
     */
    public static String signMd5(String qs, String timestamp, String nonceStr, String key) {
        return SecureUtil.md5(String.format("%s&%s=%s&%s=%s&%s=%s",
                qs,
                CommonConstants.SIGN_AUTH_TIMESTAMP, timestamp,
                CommonConstants.SIGN_AUTH_NONCE_STR, nonceStr,
                CommonConstants.SIGN_AUTH_KEY, key)).toLowerCase();
    }
}
