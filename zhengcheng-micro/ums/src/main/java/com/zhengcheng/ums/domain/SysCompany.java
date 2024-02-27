package com.zhengcheng.ums.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhengcheng.mybatis.plus.model.BaseEntity;

import java.util.Date;

import cn.hutool.core.text.CharSequenceUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 公司信息对象 sys_company
 *
 * @author ruoyi
 * @date 2020-07-11
 */
@Slf4j
@Data
public class SysCompany extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 有效截止时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activeTime;

    /**
     * 是否激活
     */
    private Integer activeFlag;

    /**
     * 模板ID
     */
    private String tempId;

    /**
     * 公司代码
     */
    private Integer comCode;

    /**
     * 模板名
     */
    private String tempName;

    /**
     * 行政区域编码
     */
    private String regionCodes;


    /**
     * 通过activeFlag和activeTime判断租户有效性
     */
    public boolean isActive() {
        if (CharSequenceUtil.isBlank(id)) { //系统管理员没有公司（租户）ID
            return true;
        }
        return activeFlag != null && activeFlag == 1 && (activeTime == null || activeTime.after(new Date()));
    }
}
