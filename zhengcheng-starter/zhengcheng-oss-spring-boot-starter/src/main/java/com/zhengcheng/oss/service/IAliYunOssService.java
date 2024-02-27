package com.zhengcheng.oss.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * 阿里云OSS
 *
 * @author : quansheng.zhang
 * @date : 2020/3/2 17:50
 */
public interface IAliYunOssService {

    /**
     * 上传文件
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            文件KEY
     * @param input
     *            文件流
     * @return 上传结果
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    PutObjectResult putObject(String bucketName, String key, InputStream input) throws OSSException, ClientException;

    /**
     * 上传文件（bucketName = ${oss.bucket-name}）
     *
     * @param key
     *            文件KEY
     * @param input
     *            文件流
     * @return 上传结果
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    PutObjectResult putObject(String key, InputStream input) throws OSSException, ClientException;

    /**
     * 上传文件
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            文件KEY
     * @param file
     *            文件
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    PutObjectResult putObject(String bucketName, String key, File file) throws OSSException, ClientException;

    /**
     * 上传文件（bucketName = ${oss.bucket-name}）
     *
     * @param key
     *            文件KEY
     * @param file
     *            文件
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    PutObjectResult putObject(String key, File file) throws OSSException, ClientException;

    /**
     * 删除文件
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            文件KEY
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    void deleteObject(String bucketName, String key) throws OSSException, ClientException;

    /**
     * 删除文件（bucketName = ${oss.bucket-name}）
     *
     * @param key
     *            文件KEY
     * @throws OSSException
     *             OSS异常
     * @throws ClientException
     *             客户端异常
     */
    void deleteObject(String key) throws OSSException, ClientException;

    /**
     * 获取临时授权签名URL-有效时间1个小时
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            文件KEY
     * @return URL
     * @throws ClientException
     *             客户端异常
     */
    URL generatePreSignedUrl(String bucketName, String key) throws ClientException;

    /**
     * 获取临时授权签名URL-有效时间1个小时 （bucketName = ${oss.bucket-name}）
     *
     * @param key
     *            文件KEY
     * @return URL
     * @throws ClientException
     *             客户端异常
     */
    URL generatePreSignedUrl(String key) throws ClientException;

    /**
     * 获取临时授权签名URL
     *
     * @param bucketName
     *            Bucket name.
     * @param expireTime
     *            有效时间，单位秒
     * @param key
     *            文件KEY
     * @return URL
     * @throws ClientException
     *             客户端异常
     */
    URL generatePreSignedUrl(String bucketName, long expireTime, String key) throws ClientException;

    /**
     * 获取临时授权签名URL （bucketName = ${oss.bucket-name}）
     *
     * @param expireTime
     *            有效时间，单位秒
     * @param key
     *            文件KEY
     * @return URL
     * @throws ClientException
     *             客户端异常
     */
    URL generatePreSignedUrl(long expireTime, String key) throws ClientException;

    /**
     * 获取永久的访问 Http URL
     *
     * @param domain
     *            CDN域名
     * @param key
     *            文件KEY
     * @return 访问地址
     */
    String getHttpUrl(String domain, String key);

    /**
     * 获取永久的访问 Http URL （bucketName = ${oss.domain}）
     *
     * @param key
     *            文件KEY
     * @return 访问地址
     */
    String getHttpUrl(String key);

    /**
     * 获取永久的访问 Https URL
     *
     * @param domain
     *            CDN域名
     * @param key
     *            文件KEY
     * @return 访问地址
     */
    String getHttpsUrl(String domain, String key);

    /**
     * 获取永久的访问 Https URL （bucketName = ${oss.domain}）
     *
     * @param key
     *            文件KEY
     * @return 访问地址
     */
    String getHttpsUrl(String key);

    /**
     * 初始化一个Multipart Upload事件
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            文件KEY
     * @return InitiateMultipartUploadResult
     */
    InitiateMultipartUploadResult initiateMultipartUpload(String bucketName, String key);

    /**
     * 初始化一个Multipart Upload事件
     *
     * @param request
     *            InitiateMultipartUploadResult
     * @return InitiateMultipartUploadResult
     */
    InitiateMultipartUploadResult initiateMultipartUpload(InitiateMultipartUploadRequest request);

//    /**
//     * 分片上传
//     *
//     * @param bucketName
//     *            Bucket name.
//     * @param key
//     *            文件KEY
//     * @param uploadId
//     *            The multipart upload Id.
//     * @param base64
//     *            文件流 Base64
//     * @param partSize
//     *            分片大小
//     * @param partNumber
//     *            每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
//     * @return UploadPartResult
//     * @throws IOException
//     *             exception
//     */
//    UploadPartResult uploadPart(String bucketName, String key, String uploadId, String base64, long partSize,
//                                int partNumber) throws IOException;

    /**
     * 分片上传
     *
     * @param request
     *            UploadPartRequest
     * @return UploadPartResult
     */
    UploadPartResult uploadPart(UploadPartRequest request);

    /**
     * 调用CompleteMultipartUpload接口来完成整个文件的分片上传
     *
     * @param bucketName
     *            Bucket name.
     * @param key
     *            Object key.
     * @param uploadId
     *            Mutlipart upload Id.
     * @param partETags
     *            The Etags for the parts.
     * @return CompleteMultipartUploadResult
     */
    CompleteMultipartUploadResult completeMultipartUpload(String bucketName, String key, String uploadId,
                                                          List<PartETag> partETags);

    /**
     * 完成整个文件的分片上传
     *
     * @param request
     *            CompleteMultipartUploadRequest
     * @return CompleteMultipartUploadResult
     */
    CompleteMultipartUploadResult completeMultipartUpload(CompleteMultipartUploadRequest request);

    /**
     * 取消分片上传
     *
     * @param request
     *            AbortMultipartUploadRequest
     */
    void abortMultipartUpload(AbortMultipartUploadRequest request);

    /**
     * 列举已上传的分片
     *
     * @param request
     *            ListPartsRequest
     * @return PartListing
     */
    PartListing listParts(ListPartsRequest request);

    /**
     * 列举分片上传事件
     *
     * @param request
     *            ListMultipartUploadsRequest
     * @return MultipartUploadListing
     */
    MultipartUploadListing listMultipartUploads(ListMultipartUploadsRequest request);

}
