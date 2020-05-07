package com.zhengcheng.common.util;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.zhengcheng.common.constant.CommonConstants;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 签名验证工具类
 *
 * @author :    zhangquansheng
 * @date :    2020/5/7 18:54
 */
public class SignAuthUtils {

    /**
     * 按照字顺序进行升序排序
     *
     * @param params 请求参数
     * @return 排序后结果
     */
    public static String sortQueryParamString(Map<String, Object> params) {
        List<String> listKeys = Lists.newArrayList(params.keySet());
        Collections.sort(listKeys);
        StrBuilder content = StrBuilder.create();
        for (String param : listKeys) {
            content.append(param).append("=").append(params.get(param).toString()).append("&");
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
     * @return
     */
    public static String signMd5(String qs, String timestamp, String nonceStr, String key) {
        return SecureUtil.md5(String.format("%s&%=%s&%=%s&s=%s",
                qs,
                CommonConstants.SIGN_AUTH_TIMESTAMP, timestamp,
                CommonConstants.SIGN_AUTH_NONCE_STR, nonceStr,
                CommonConstants.SIGN_AUTH_KEY, key)).toLowerCase();
    }
}
