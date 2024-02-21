package com.zhengcheng.magic.controller.facade.internal.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * HouseGroupDTO
 *
 * @author quansheng1.zhang
 * @since 2021/5/26 16:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseGroupDTO implements Serializable {

    /**
     * 楼盘区域
     */
    private String lpArea;
    /**
     * 价格(最高均价，最低均价，平均均价)
     */
    private Integer price;
    /**
     * 年份/月份
     */
    private String yearMonth;
}
