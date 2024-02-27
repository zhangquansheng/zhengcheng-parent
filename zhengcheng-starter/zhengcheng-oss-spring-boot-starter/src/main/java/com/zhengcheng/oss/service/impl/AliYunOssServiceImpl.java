package com.zhengcheng.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.AbortMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListMultipartUploadsRequest;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.MultipartUploadListing;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.zhengcheng.oss.properties.OssProperties;
import com.zhengcheng.oss.service.IAliYunOssService;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * PostObjectPolicyServiceImpl
 *
 * @author : quansheng.zhang
 * @date : 2020/3/2 17:52
 */
@Slf4j
public class AliYunOssServiceImpl implements IAliYunOssService {

    private final OssProperties ossProperties;
    private final OSSClient client;

    public AliYunOssServiceImpl(OssProperties ossProperties, OSSClient client) {
        this.ossProperties = ossProperties;
        this.client = client;
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, InputStream input)
            throws OSSException, ClientException {
        return client.putObject(bucketName, key, input);
    }

    @Override
    public PutObjectResult putObject(String key, InputStream input) throws OSSException, ClientException {
        return this.putObject(ossProperties.getBucketName(), key, input);
    }

    @Override
    public PutObjectResult putObject(String bucketName, String key, File file) throws OSSException, ClientException {
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        // 上传文件。
        return client.putObject(putObjectRequest);
    }

    @Override
    public PutObjectResult putObject(String key, File file) throws OSSException, ClientException {
        return this.putObject(ossProperties.getBucketName(), key, file);
    }

    @Override
    public void deleteObject(String bucketName, String key) throws OSSException, ClientException {
        client.deleteObject(bucketName, key);
    }

    @Override
    public void deleteObject(String key) throws OSSException, ClientException {
        deleteObject(ossProperties.getBucketName(), key);
    }

    @Override
    public URL generatePreSignedUrl(String bucketName, String key) throws ClientException {
        // 设置URL过期时间为1小时。
        return this.generatePreSignedUrl(bucketName, 3600, key);
    }

    @Override
    public URL generatePreSignedUrl(String key) {
        // 设置URL过期时间为1小时。
        return this.generatePreSignedUrl(ossProperties.getBucketName(), 3600, key);
    }

    @Override
    public URL generatePreSignedUrl(String bucketName, long expireTime, String key) throws ClientException {
        Date expiration = new Date(System.currentTimeMillis() + expireTime * 1000);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        return client.generatePresignedUrl(bucketName, key, expiration);
    }

    @Override
    public URL generatePreSignedUrl(long expireTime, String key) throws ClientException {
        return generatePreSignedUrl(ossProperties.getBucketName(), expireTime, key);
    }

    @Override
    public String getHttpUrl(String domain, String key) {
        return StrUtil.format("http://{}/{}", domain, key);
    }

    @Override
    public String getHttpUrl(String key) {
        return StrUtil.format("http://{}/{}", ossProperties.getDomain(), key);
    }

    @Override
    public String getHttpsUrl(String domain, String key) {
        return StrUtil.format("https://{}/{}", domain, key);
    }

    @Override
    public String getHttpsUrl(String key) {
        return StrUtil.format("https://{}/{}", ossProperties.getDomain(), key);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(String bucketName, String key) {
        // 创建InitiateMultipartUploadRequest对象。
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        return this.initiateMultipartUpload(request);
    }

    @Override
    public InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request) {
        return client.initiateMultipartUpload(request);
    }

//    @Override
//    public UploadPartResult uploadPart(String bucketName, String key, String uploadId, String base64, long partSize,
//                                       int partNumber) throws IOException {
//        UploadPartRequest uploadPartRequest = new UploadPartRequest();
//        uploadPartRequest.setBucketName(bucketName);
//        uploadPartRequest.setKey(key);
//        uploadPartRequest.setUploadId(uploadId);
//        uploadPartRequest.setInputStream(new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(base64)));
//        uploadPartRequest.setPartSize(partSize);
//        uploadPartRequest.setPartNumber(partNumber);
//        return this.uploadPart(uploadPartRequest);
//    }

    @Override
    public UploadPartResult uploadPart(UploadPartRequest request) {
        return client.uploadPart(request);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(String bucketName, String key, String uploadId,
                                                                 List<PartETag> partETags) {
        // 创建CompleteMultipartUploadRequest对象。
        // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        // 完成上传。
        return client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    @Override
    public CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request) {
        return client.completeMultipartUpload(request);
    }

    @Override
    public void abortMultipartUpload(AbortMultipartUploadRequest request) {
        client.abortMultipartUpload(request);
    }

    @Override
    public PartListing listParts(ListPartsRequest request) {
        return client.listParts(request);
    }

    @Override
    public MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request) {
        return client.listMultipartUploads(request);
    }

}
