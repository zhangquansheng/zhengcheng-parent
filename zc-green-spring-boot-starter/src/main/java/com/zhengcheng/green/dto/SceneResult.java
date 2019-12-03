package com.zhengcheng.green.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.zhengcheng.green.constant.AliYunGreenConstants;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 检测返回结果
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 21:23
 */
@Data
public class SceneResult implements Serializable {
    /**
     * 错误码，和HTTP的status code一致。
     */
    private int code;
    /**
     * 错误描述信息。
     */
    private String msg;
    /**
     * 对应请求的dataId。
     */
    private String dataId;
    /**
     * 该检测任务的ID。
     */
    private String taskId;
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
    private List<Result> results;

    /**
     * 检测是否通过
     *
     * @return 是否通过
     */
    public boolean pass() {
        if (AliYunGreenConstants.OK == this.code && CollectionUtil.isNotEmpty(results)) {
            for (Result result : results) {
                if (!"pass".equalsIgnoreCase(result.getSuggestion())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
