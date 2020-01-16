package com.zhengcheng.green.service;

import com.tencentcloudapi.cms.v20190321.models.ImageModerationResponse;
import com.tencentcloudapi.cms.v20190321.models.TextModerationResponse;

/**
 * 腾讯云 CMS 内容安全接口
 *
 * @author :    zhangquansheng
 * @date :    2020/1/16 17:18
 */
public interface ITencentcloudCmsService {

    /**
     * 文本内容检测
     *
     * @param content 文本内容
     * @return 输出参数
     */
    TextModerationResponse text(String content);

    /**
     * 图片内容检测
     *
     * @param fileContent 文件内容 Base64
     * @return 输出参数
     */
    ImageModerationResponse imageFileContent(String fileContent);

    /**
     * 图片内容检测
     *
     * @param fileMD5 文件MD5值
     * @return 输出参数
     */
    ImageModerationResponse imageFileMD5(String fileMD5);

    /**
     * 图片内容检测
     *
     * @param fileUrl 文件地址
     * @return 输出参数
     */
    ImageModerationResponse imageFileUrl(String fileUrl);
}
