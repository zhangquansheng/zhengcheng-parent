package com.zhengcheng.web.support;

import com.zhengcheng.common.support.DateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * Controller - 基类
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/16 23:27
 */
public class BaseController {

    /**
     * 数据绑定
     * 此方法用于日期的转换，如果未加，当页面日期格式转换错误，将报400错误，实际是因为此方法
     *
     * @param binder WebDataBinder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new DateEditor(true));
    }
}
