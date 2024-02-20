package com.zhengcheng.data.elasticsearch.annotations;

/**
 * 分词器类型
 */
public enum Analyzer {
    /**
     * 按照 field > document的优先级，如果都是 AUTO，则返回 "standard"
     */
    AUTO,
    /**
     * 支持中文采用的方法为单字切分。他会将词汇单元转换成小写形式，并去除停用词和标点符号
     */
    STANDARD,
    /**
     * ik中文智能分词 https://github.com/medcl/elasticsearch-analysis-ik/
     */
    IK_SMART,
    /**
     * ik中文分词
     */
    IK_MAX_WORD;
}
