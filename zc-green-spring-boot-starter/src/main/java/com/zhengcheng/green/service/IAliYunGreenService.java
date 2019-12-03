package com.zhengcheng.green.service;

import com.zhengcheng.green.dto.SceneResult;
import com.zhengcheng.green.dto.TextSceneData;
import lombok.NonNull;

import java.util.List;

/**
 * IAliYunGreenService
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:25
 */
public interface IAliYunGreenService {

    /**
     * 文本垃圾检测
     *
     * @param dataId  数据ID
     * @param content 待检测的文本，长度不超过10000个字符
     * @return 检测结果
     */
    SceneResult antispam(@NonNull String dataId, @NonNull String content);

    /**
     * 批量文本垃圾检测
     *
     * @param textSceneDataList 待检测的文本数据
     * @return 检测结果
     */
    List<SceneResult> batchAntispam(List<TextSceneData> textSceneDataList);
}
