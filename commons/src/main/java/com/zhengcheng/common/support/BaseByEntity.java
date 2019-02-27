package com.zhengcheng.common.support;

import lombok.Data;

/**
 * BaseByEntity
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.support
 * @Description :
 * @date :    2019/2/2 13:34
 */
@Data
public class BaseByEntity extends BaseEntity {
    private static final long serialVersionUID = -5271032942724588675L;
    private Long createBy;
    private Long updateBy;
}
