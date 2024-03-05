package com.zhengcheng.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 日志实体类 sys_log
 *
 * @author zhangquansheng
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log_oper")
public class SysOperLogEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 日志主键
     */
//    @TableId("oper_id")
    private Long operId;

    /**
     * 服务名称，一般为spring.application.name
     */
    private String appName;

    /**
     * 操作模块
     */
    private String logName;

    /**
     * 日志记录内容
     */
    private String logContent;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 操作人员 id
     */
    private Long operUserId;

    /**
     * 请求url
     */
    private String operUrl;

    /**
     * 操作地址
     */
    private String operIp;


    /**
     * 请求参数
     */
    private String operParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private Integer status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operTime;

    @TableField(exist = false)
    private Map<String, Object> params = new HashMap<>();
}
