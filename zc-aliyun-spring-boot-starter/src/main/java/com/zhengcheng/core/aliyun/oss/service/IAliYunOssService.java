package com.zhengcheng.core.aliyun.oss.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import com.zhengcheng.core.aliyun.oss.dto.PostObjectPolicyDTO;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * 阿里云OSS
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/2 17:50
 */
public interface IAliYunOssService {

    /**
     * 获取服务器签名
     *
     * @return 签名
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    PostObjectPolicyDTO getPostObjectPolicy() throws UnsupportedEncodingException;

    /**
     * 获取服务器签名
     *
     * @param expireTime 有效时间，单位秒
     * @param dir        目录
     * @return 签名
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    PostObjectPolicyDTO getPostObjectPolicy(long expireTime, String dir) throws UnsupportedEncodingException;

    /**
     * 上传文件
     *
     * @param key   文件KEY
     * @param input 文件流
     * @return 上传结果
     * @throws OSSException    OSS异常
     * @throws ClientException 客户端异常
     */
    PutObjectResult putObject(String key, InputStream input) throws OSSException, ClientException;

    /**
     * 删除文件
     *
     * @param key 文件KEY
     * @throws OSSException    OSS异常
     * @throws ClientException 客户端异常
     */
    void deleteObject(String key) throws OSSException, ClientException;

    /**
     * 获取临时授权签名URL-有效时间1个小时
     *
     * @param key 文件KEY
     * @return URL
     * @throws ClientException 客户端异常
     */
    URL generatePreSignedUrl(String key) throws ClientException;

    /**
     * 获取临时授权签名URL
     *
     * @param expireTime 有效时间，单位秒
     * @param key        文件KEY
     * @return URL
     * @throws ClientException 客户端异常
     */
    URL generatePreSignedUrl(long expireTime, String key) throws ClientException;
}
