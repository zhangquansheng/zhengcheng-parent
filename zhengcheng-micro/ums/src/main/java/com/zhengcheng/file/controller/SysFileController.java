package com.zhengcheng.file.controller;

import com.zhengcheng.common.domain.Result;
import com.zhengcheng.file.domain.SysFile;
import com.zhengcheng.file.service.SysFileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件请求处理
 */
@Slf4j
@RestController
public class SysFileController {

    @Autowired
    private SysFileService sysFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("upload")
    public Result upload(MultipartFile file) {
        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            SysFile sysFile = new SysFile();
            sysFile.setName(FileUtil.getName(url));
            sysFile.setUrl(url);
            return Result.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return Result.errorMessage(e.getMessage());
        }
    }
}
