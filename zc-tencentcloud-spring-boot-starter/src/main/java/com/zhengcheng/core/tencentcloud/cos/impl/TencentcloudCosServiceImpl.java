package com.zhengcheng.core.tencentcloud.cos.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.*;
import com.zhengcheng.core.tencentcloud.cos.properties.CosProperties;
import com.zhengcheng.core.tencentcloud.cos.ITencentcloudCosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * TencentcloudCosServiceImpl
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/4 20:33
 */
@Slf4j
@RequiredArgsConstructor
public class TencentcloudCosServiceImpl implements ITencentcloudCosService {

    private final CosProperties cosProperties;
    private final COSClient cosClient;

    @Override
    public Bucket createBucket(String bucket) throws CosClientException {
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        return this.createBucket(bucket, CannedAccessControlList.Private);
    }

    @Override
    public Bucket createBucket(String bucket, CannedAccessControlList cannedAccessControlList) throws CosClientException {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        createBucketRequest.setCannedAcl(cannedAccessControlList);
        return cosClient.createBucket(createBucketRequest);
    }

    @Override
    public List<Bucket> listBuckets() {
        return cosClient.listBuckets();
    }

    @Override
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), key, file);
        return cosClient.putObject(putObjectRequest);
    }

    @Override
    public PutObjectResult putObject(String key, InputStream input, ObjectMetadata metadata) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosProperties.getBucketName(), key, input, metadata);
        return cosClient.putObject(putObjectRequest);
    }

    @Override
    public void deleteObject(String key) {
        cosClient.deleteObject(cosProperties.getBucketName(), key);
    }

}
