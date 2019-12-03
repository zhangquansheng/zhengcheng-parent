package com.zhengcheng.green.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 检测结果
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:29
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SceneResult {
    /**
     * 检测场景
     */
    private String scene;
    /**
     * suggestion == pass 未命中垃圾  suggestion == block 命中了垃圾，可以通过label字段查看命中的垃圾分类
     */
    private String suggestion;
}
