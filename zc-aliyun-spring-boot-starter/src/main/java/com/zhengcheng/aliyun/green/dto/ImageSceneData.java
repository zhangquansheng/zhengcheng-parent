package com.zhengcheng.aliyun.green.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 图片检测
 *
 * @author :    zhangquansheng
 * @date :    2019/12/3 14:05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageSceneData extends BaseSceneData {
    /**
     * 图片链接
     */
    private String url;
}
