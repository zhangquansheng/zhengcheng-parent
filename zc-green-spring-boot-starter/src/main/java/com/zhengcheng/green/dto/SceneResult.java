package com.zhengcheng.green.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 检测结果
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/2 23:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SceneResult extends BaseSceneData {
    /**
     * 检测场景
     */
    private String scene;
    /**
     * suggestion == pass 未命中垃圾  suggestion == block 命中了垃圾，可以通过label字段查看命中的垃圾分类
     */
    private String suggestion;

    /**
     * 检测是否通过
     *
     * @return 是否通过
     */
    public boolean pass() {
        return "pass".equalsIgnoreCase(this.suggestion);
    }
}
