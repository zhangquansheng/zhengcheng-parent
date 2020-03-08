package com.zhengcheng.aliyun.oss.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.zhengcheng.aliyun.oss.dto.PostObjectPolicyDTO;
import com.zhengcheng.aliyun.oss.properties.OssProperties;
import com.zhengcheng.aliyun.oss.service.IAliYunOssService;
import com.zhengcheng.aliyun.properties.AliYunProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Date;

/**
 * PostObjectPolicyServiceImpl
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/2 17:52
 */
@Slf4j
@RequiredArgsConstructor
public class AliYunOssServiceImpl implements IAliYunOssService {


    private final AliYunProperties aliyunProperties;
    private final OssProperties ossProperties;
    private final OSSClient client;

    @Override
    public PostObjectPolicyDTO getPostObjectPolicy() throws UnsupportedEncodingException {
        return this.getPostObjectPolicy(30, "");
    }

    @Override
    public PostObjectPolicyDTO getPostObjectPolicy(long expireTime, String dir) throws UnsupportedEncodingException {
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        Date expiration = new Date(expireEndTime);
        PolicyConditions policyConditions = new PolicyConditions();
        policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        if (StrUtil.isNotEmpty(dir)) {
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
        }

        String postPolicy = client.generatePostPolicy(expiration, policyConditions);
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

    @Override
    public PutObjectResult putObject(String key, InputStream input) throws OSSException, ClientException {
        return client.putObject(ossProperties.getBucketName(), key, input);
    }

    @Override
    public void deleteObject(String key) throws OSSException, ClientException {
        client.deleteObject(ossProperties.getBucketName(), key);
    }

    @Override
    public URL generatePreSignedUrl(String key) {
        // 设置URL过期时间为1小时。
        return this.generatePreSignedUrl(3600, key);
    }

    @Override
    public URL generatePreSignedUrl(long expireTime, String key) throws ClientException {
        Date expiration = new Date(System.currentTimeMillis() + expireTime * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        return client.generatePresignedUrl(ossProperties.getBucketName(), key, expiration);
    }
}
