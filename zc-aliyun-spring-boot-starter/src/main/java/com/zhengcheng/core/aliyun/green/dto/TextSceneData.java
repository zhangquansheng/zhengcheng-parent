package com.zhengcheng.core.aliyun.green.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文本内容检测
 *
 * @author :    zhangquansheng
 * @date :    2019/12/3
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TextSceneData extends BaseSceneData {

    /**
     * 待检测的文本，长度不超过10000个字符
     */
    private String content;
}
