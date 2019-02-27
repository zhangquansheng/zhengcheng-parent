package com.zhengcheng.common.support;

import lombok.Data;

/**
 * BaseByRankEntity
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.support
 * @Description :
 * @date :    2019/2/2 13:39
 */
@Data
public class BaseByRankEntity extends BaseByEntity {
    private static final long serialVersionUID = -795856028806773143L;
    private Long rank;
}
