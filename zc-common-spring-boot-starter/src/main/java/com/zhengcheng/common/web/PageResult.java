package com.zhengcheng.common.web;

import com.zhengcheng.common.constant.CommonConstants;
import com.zhengcheng.common.enumeration.CodeEnum;
import lombok.Data;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 *
 * @author :    zhangquansheng
 * @date :    2020/4/26 14:33
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 556110794424864630L;
    /**
     * 总数
     */
    private Long total;
    private Integer code;
    private String message;
    private List<T> data;
    private String requestId;

    public static <T> PageResult<T> successData(Long total, List<T> data) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setCode(CodeEnum.SUCCESS.getCode());
        pageResult.setMessage(CodeEnum.SUCCESS.getMessage());
        pageResult.setData(data);
        pageResult.setRequestId(MDC.get(CommonConstants.TRACE_ID));
        return pageResult;
    }

    public static <T> PageResult<T> successData(PageInfo<T> pageInfo) {
        return PageResult.successData(pageInfo.getTotal(), pageInfo.getList());
    }
}
