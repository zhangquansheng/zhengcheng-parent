package com.zhengcheng.aliyun.oss.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.zhengcheng.aliyun.oss.dto.PostObjectPolicyDTO;
import com.zhengcheng.aliyun.oss.properties.OssProperties;
import com.zhengcheng.aliyun.oss.service.IPostObjectPolicyService;
import com.zhengcheng.aliyun.properties.AliYunProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * PostObjectPolicyServiceImpl
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/2 17:52
 */
@Slf4j
@RequiredArgsConstructor
public class PostObjectPolicyServiceImpl implements IPostObjectPolicyService {


    private final AliYunProperties aliyunProperties;
    private final OssProperties ossProperties;
    private final OSSClient client;

    @Override
    public PostObjectPolicyDTO get() throws UnsupportedEncodingException {
        String dir = "user-dir";
        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes("utf-8");
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);
        PostObjectPolicyDTO postObjectPolicyDTO = new PostObjectPolicyDTO();
        postObjectPolicyDTO.setAccessid(aliyunProperties.getAccessKey());
        postObjectPolicyDTO.setPolicy(encodedPolicy);
        postObjectPolicyDTO.setSignature(postSignature);
        postObjectPolicyDTO.setDir(dir);
        postObjectPolicyDTO.setHost(ossProperties.getDomain());
        postObjectPolicyDTO.setExpire(String.valueOf(expireEndTime / 1000));
        return postObjectPolicyDTO;
    }
}
