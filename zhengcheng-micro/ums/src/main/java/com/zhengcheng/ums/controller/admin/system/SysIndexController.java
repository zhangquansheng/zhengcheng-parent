package com.oddfar.campus.admin.controller.system;

import com.zhengcheng.common.domain.Result;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 首页
 *
 * @author oddfar
 */
@RestController
public class SysIndexController {

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index() {
        return "";
    }

    @Value("${campus.version}")
    private String version;

    @Value("${campus.frameworkVersion}")
    private String frameworkVersion;

    /**
     * 版本情况
     */
    @RequestMapping("/version")
    public Result version() {
        HashMap<String, String> map = new HashMap<>();
        map.put("version", version);
        map.put("frameworkVersion", frameworkVersion);
        return Result.ok(map);
    }
}
