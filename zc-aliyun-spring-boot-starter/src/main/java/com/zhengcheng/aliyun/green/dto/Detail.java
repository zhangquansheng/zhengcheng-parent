package com.zhengcheng.aliyun.green.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Detail
 *
 * @author :    quansheng.zhang
 * @date :    2019/12/3 21:17
 */
@Data
public class Detail implements Serializable {
    /**
     * 文本命中风险的分类，与具体的scene对应。取值范围参考scene 和 label说明。
     */
    private String label;
    /**
     * 命中该风险的上下文信息
     */
    private List<Context> contexts;
    /**
     * 文本命中的关键词信息，用于提示您违规的原因，可能会返回多个命中的关键词
     */
    private List<HintWord> hintWords;
}
