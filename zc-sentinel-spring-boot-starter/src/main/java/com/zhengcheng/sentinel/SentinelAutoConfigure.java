package com.zhengcheng.sentinel;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.zhengcheng.common.web.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sentinel配置类
 *
 * @author :    quansheng.zhang
 * @date :    2019/7/30 1:27
 */
public class SentinelAutoConfigure {

    public SentinelAutoConfigure() {
        WebCallbackManager.setUrlBlockHandler(new CustomUrlBlockHandler());
    }

    /**
     * 限流、熔断统一处理类
     */
    public class CustomUrlBlockHandler implements UrlBlockHandler {
        @Override
        public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
            Result result = Result.errorMessage("系统繁忙，请稍后重试！");
            httpServletResponse.getWriter().print(JSON.toJSONString(result));
        }
    }
}
