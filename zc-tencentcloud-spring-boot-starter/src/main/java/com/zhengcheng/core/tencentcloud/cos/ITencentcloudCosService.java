package com.zhengcheng.core.tencentcloud.cos;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.CannedAccessControlList;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 腾讯云对象存储 COS
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/4 20:31
 */
public interface ITencentcloudCosService {

    /**
     * 创建存储桶-默认私有读写
     *
     * @param bucket 存储桶名称，格式：BucketName-APPID
     * @return 存储桶
     */
    Bucket createBucket(String bucket) throws CosClientException;

    /**
     * 创建存储桶
     *
     * @param bucket                  存储桶名称，格式：BucketName-APPID
     * @param cannedAccessControlList bucket 的权限
     * @return 存储桶
     */
    Bucket createBucket(String bucket, CannedAccessControlList cannedAccessControlList) throws CosClientException;

    /**
     * 查询存储桶列表
     *
     * @return 存储桶列表
     */
    List<Bucket> listBuckets();

    /**
     * 上传文件
     *
     * @param key  文件KEY
     * @param file 文件
     * @return 上传结果
     */
    PutObjectResult putObject(String key, File file);

    /**
     * 上传文件
     *
     * @param key      文件KEY
     * @param input    文件流
     * @param metadata 对象数据类型
     * @return 上传结果
     */
    PutObjectResult putObject(String key, InputStream input, ObjectMetadata metadata);

    /**
     * 删除对象
     *
     * @param key COS 上指定路径的对象
     */
    void deleteObject(String key);
}
