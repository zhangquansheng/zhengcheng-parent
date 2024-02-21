package com.zhengcheng.magic.passport.justauth.controller;

import com.zhengcheng.magic.passport.justauth.JustAuthPlatformInfo;
import com.zhengcheng.magic.passport.justauth.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * JustAuthController
 *
 * @author quansheng1.zhang
 * @since 2024/1/31 11:57
 */
@Controller
@RequestMapping
public class JustAuthController {

    @Value("${server.port}")
    public int port;

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public ModelAndView index() {
        Map<String, Object> map = new HashMap<>();
        map.put("enableAuthPlatforms", JustAuthPlatformInfo.getPlatformInfos());
        map.put("port", port);
        return new ModelAndView("index", map);
    }

    @RequestMapping("/users")
    public ModelAndView users() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("users", userService.listAll());
        return new ModelAndView("users", map);
    }


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object handle(Throwable e) {
        e.printStackTrace();
        return e.getMessage();
    }

}
