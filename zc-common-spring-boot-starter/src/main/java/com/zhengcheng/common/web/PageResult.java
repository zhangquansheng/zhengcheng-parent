package com.zhengcheng.common.web;

import com.zhengcheng.common.constant.CommonConstants;
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
     * 错误码，和HTTP状态码一致（但有扩展）。
     * 2xx：表示调用成功
     * 4xx：表示请求有误
     * 5xx：表示后端有误
     */
    private Integer code;
    private String message;
    private String requestId;
    /**
     * 总数
     */
    private Long total;
    /**
     * 数据
     */
    private List<T> data;

    public static <T> PageResult<T> successData(Long total, List<T> data) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setData(data);
        pageResult.setCode(CodeEnum.SUCCESS.getCode());
        pageResult.setMessage(CodeEnum.SUCCESS.getMessage());
        pageResult.setRequestId(MDC.get(CommonConstants.TRACE_ID));
        return pageResult;
    }

    public static <T> PageResult<T> successData(PageInfo<T> pageInfo) {
        return PageResult.successData(pageInfo.getTotal(), pageInfo.getList());
    }

}
