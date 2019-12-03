package com.zhengcheng.green.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.zhengcheng.green.constant.AliYunGreenConstants;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * ImageSceneResult
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 22:12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ImageSceneResult extends BaseSceneResult {
    /**
     * 对应请求中的URL。
     */
    private String url;
    /**
     * 额外附加信息
     */
    private Map extras;
    /**
     * 返回结果。调用成功时（code=200），返回结果中包含一个或多个元素
     */
    private List<ImageResult> results;

    /**
     * 检测是否通过
     *
     * @return 是否通过
     */
    public boolean pass() {
        if (AliYunGreenConstants.OK == getCode() && CollectionUtil.isNotEmpty(results)) {
            for (ImageResult result : results) {
                if (!"pass".equalsIgnoreCase(result.getSuggestion())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
