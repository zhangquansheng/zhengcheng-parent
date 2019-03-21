package com.zhengcheng.common.support;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * The class Base entity.
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.support
 * @Description :
 * @date :    2019/2/2
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8126755480334740840L;

    @Id
    // MySQL/SQLServer: @GeneratedValue(strategy = GenerationType.AUTO)
    // Oracle: @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gmtModified;
}
