package com.zhengcheng.green.service;

import com.zhengcheng.green.dto.ImageSceneData;
import com.zhengcheng.green.dto.ImageSceneResult;
import com.zhengcheng.green.dto.TextSceneData;
import com.zhengcheng.green.dto.TextSceneResult;
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
    TextSceneResult antispam(@NonNull String dataId, @NonNull String content);

    /**
     * 批量文本垃圾检测
     *
     * @param textSceneDataList 待检测的文本数据
     * @return 检测结果
     */
    List<TextSceneResult> batchAntispam(List<TextSceneData> textSceneDataList);

    /**
     * 批量图片同步检测
     *
     * @param scenes             设置要检测的场景, 计费是按照该处传递的场景进行
     *                           一次请求中可以同时检测多张图片，每张图片可以同时检测多个风险场景，计费按照场景计算
     *                           例如：检测2张图片，场景传递porn、terrorism，计费会按照2张图片鉴黄，2张图片暴恐检测计算
     *                           porn: porn表示色情场景检测
     * @param imageSceneDataList 待检测的图片数据
     * @return
     */
    List<ImageSceneResult> batchImageSyncScan(List<String> scenes, List<ImageSceneData> imageSceneDataList);
}
