package com.zhengcheng.ums.common.manager;


import com.zhengcheng.mvc.utils.IpUtil;
import com.zhengcheng.ums.common.constant.Constants;
import com.zhengcheng.ums.domain.entity.SysLoginLogEntity;
import com.zhengcheng.ums.service.SysLoginLogService;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;


/**
 * 异步工厂（产生任务用）
 *
 * @author ruoyi
 */
public class AsyncFactory {

    /**
     * 记录登录信息
     *
     * @param userName 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static Runnable recordLogininfor(final String userName, final Long userId, final String status, final String message) {
        final UserAgent userAgent = UserAgentUtil.parse(IpUtil.getRequest().getHeader("User-Agent"));
        final String ip = IpUtil.getIpAddr();
        return () -> {
            // 获取客户端操作系统
            String os = userAgent.getOs().getName();
            // 获取客户端浏览器
            String browser = userAgent.getBrowser().getName();
            // 封装对象
            SysLoginLogEntity logininfor = new SysLoginLogEntity();
            logininfor.setLoginTime(new Date());
            logininfor.setUserName(userName);
            logininfor.setUserId(userId);
            logininfor.setIpaddr(ip);
            logininfor.setBrowser(browser);
            logininfor.setOs(os);
            logininfor.setMsg(message);
            // 日志状态
            if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                logininfor.setStatus(Constants.SUCCESS);
            } else if (Constants.LOGIN_FAIL.equals(status)) {
                logininfor.setStatus(Constants.FAIL);
            }
            // 插入数据
            SpringUtil.getBean(SysLoginLogService.class).insertLogininfor(logininfor);
        };
    }
}
