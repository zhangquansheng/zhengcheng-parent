package com.zhengcheng.auth.client.util;

import cn.hutool.json.JSONUtil;
import com.zhengcheng.common.web.Result;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * ResponseUtil
 *
 * @author :    quansheng.zhang
 * @date :    2019/9/24 10:03
 */
public class ResponseUtil {

    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 通过流写到前端
     *
     * @param response
     * @param httpStatus 返回状态码
     * @param result     返回信息
     * @throws IOException
     */
    public static void responseWriter(HttpServletResponse response, int httpStatus, Result result) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try (
                Writer writer = response.getWriter()
        ) {
            writer.write(JSONUtil.toJsonStr(result));
            writer.flush();
        }
    }
}
