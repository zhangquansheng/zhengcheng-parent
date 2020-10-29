package com.zhengcheng.core.aliyun.green.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * BaseResult
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 22:07
 */
@Data
public class BaseResult implements Serializable {
    private static final long serialVersionUID = 8583148192728079260L;

    /**
     * 检测场景，和调用请求中的场景对应。
     */
    private String scene;
    /**
     * 建议用户执行的操作，取值范围：
     * pass：文本正常
     * review：需要人工审核
     * block：文本违规，可以直接删除或者做限制处理
     */
    private String suggestion;
    /**
     * 检测结果的分类，与具体的scene对应
     */
    private String label;
    /**
     * 结果为该分类的概率，取值范围为[0.00-100.00]。值越高，表示越有可能属于该分类。
     */
    private float rate;
}
