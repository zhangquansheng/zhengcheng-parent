package com.zhengcheng.green.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 基本检测返回结果
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 22:04
 */
@Data
public class BaseSceneResult implements Serializable {
    private static final long serialVersionUID = -5587294697132938244L;
    /**
     * 错误码，和HTTP的tatus code一致。
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

}
