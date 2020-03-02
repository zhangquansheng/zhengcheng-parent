package com.zhengcheng.aliyun.oss.service;

import com.zhengcheng.aliyun.oss.dto.PostObjectPolicyDTO;

import java.io.UnsupportedEncodingException;

/**
 * IPostObjectPolicyService
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/2 17:50
 */
public interface IPostObjectPolicyService {

    /**
     * 获取
     *
     * @return 签名
     */
    PostObjectPolicyDTO get() throws UnsupportedEncodingException;
}
