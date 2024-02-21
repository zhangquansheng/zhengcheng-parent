
package com.zhengcheng.knife4j.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:xiaoymin@foxmail.com">xiaoymin@foxmail.com</a>
 * 2020/09/18 11:02
 * @since:knife4j-spring-boot2-demo 1.0
 */
@Api(tags = "你好")
@RestController
@RequestMapping("/hello")
public class HelloController {

    @ApiOperation(value = "向客人问好")
    @GetMapping("/sayHi")
    public ResponseEntity<String> sayHi(@RequestParam("name") String name) {
        return ResponseEntity.ok("Hi:" + name);
    }
}
