package com.zhengcheng.aliyun.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.zhengcheng.aliyun.constant.AliYunGreenConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 文本内容检测返回结果
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 21:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextSceneResult extends BaseSceneResult {
    /**
     * 对应请求的内容.
     */
    private String content;
    /**
     * 如果检测文本命中您自定义关键词词库中的词，该字段会返回，并将命中的关键词替换为“*”。
     */
    private String filteredContent;
    /**
     * 返回结果。调用成功时（code=200），返回结果中包含一个或多个元素
     */
    private List<TextResult> results;

    /**
     * 检测是否通过
     *
     * @return 是否通过
     */
    public boolean pass() {
        if (AliYunGreenConstants.OK == getCode() && CollectionUtil.isNotEmpty(results)) {
            for (TextResult result : results) {
                if (!"pass".equalsIgnoreCase(result.getSuggestion())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
