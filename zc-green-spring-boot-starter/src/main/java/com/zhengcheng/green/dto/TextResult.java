package com.zhengcheng.green.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * TextResult
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 21:19
 */
@Data
public class TextResult extends BaseResult {
    /**
     * 附加信息，扩展字段。
     */
    private JSONObject extras;
    /**
     * 命中风险的详细信息，一条文本可能命中多条风险详情
     */
    private List<Detail> details;
}
