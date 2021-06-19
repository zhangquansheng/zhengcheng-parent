package com.zhengcheng.data.elasticsearch.test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhengcheng.data.elasticsearch.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小天才基础表-房(House) Document
 * 
 * @author quansheng1.zhang
 * @since 2021-05-13 11:41:54
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(indexName = "house", analyzer = Analyzer.IK_MAX_WORD, searchAnalyzer = Analyzer.IK_MAX_WORD)
public class HouseDocument implements Serializable {
    private static final long serialVersionUID = -52652057815041803L;

    /**
     * 主键ID
     */
    @Id
    private String id;
    /**
     * 创建人
     */
    private Integer createdUser;
    /**
     * 记录创建时间，默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private Integer updatedUser;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 楼盘ID
     */
    private Integer lpId;
    /**
     * 备案号
     */
    private Integer recordNum;
    /**
     * 名称
     */
    private String name;
    /**
     * 楼号
     */
    private String buildingNum;
    /**
     * 建筑面积
     */
    private String builtUpArea;
    /**
     * 套数
     */
    private Integer roomNum;

    /**
     * 均价
     */
    private Integer averagePrice;

    /**
     * 坐落位置
     */
    private String lpLocation;

    /**
     * 所在区域
     */
    @Field(type = FieldType.Keyword)
    private String lpArea;

    /**
     * 物业类型
     */
    private String wyType;

    /**
     * 建筑类型
     */
    private String buildType;

    /**
     * 开发企业
     */
    private String kfEnterprise;

    /**
     * 设计单位
     */
    private String sjEnterprise;

    /**
     * 楼盘物业
     */
    private String wyEnterprise;

    /**
     * 周边配套
     */
    private String rim;

    /**
     * 交通状况
     */
    private String traffic;

    /**
     * 列表页面的页码
     */
    private Integer pageNum;

    /**
     * 备案年份 yyyy
     */
    private String recordYear;

    /**
     * 备案月份 yyyyMM
     */
    private String recordMonth;
}