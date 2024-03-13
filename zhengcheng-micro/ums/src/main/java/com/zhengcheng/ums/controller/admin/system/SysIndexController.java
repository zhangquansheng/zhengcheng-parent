package com.zhengcheng.ums.controller.admin.system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
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

}
