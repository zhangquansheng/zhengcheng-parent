package com.zhengcheng.common.dto;

import com.zhengcheng.common.enumeration.CloudStorageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 云存储
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/4 21:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudStorageDTO implements Serializable {
    private static final long serialVersionUID = -9084561210261513006L;
    /**
     * 对象
     */
    private String key;
    /**
     * 云存储类型
     */
    private CloudStorageTypeEnum type;
}
