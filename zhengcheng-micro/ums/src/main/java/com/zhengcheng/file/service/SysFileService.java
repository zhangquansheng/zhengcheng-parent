package com.zhengcheng.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传接口
 */
public interface SysFileService {
    /**
     * 文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    String uploadFile(MultipartFile file) throws Exception;
}
