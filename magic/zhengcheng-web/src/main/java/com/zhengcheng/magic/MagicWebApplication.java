package com.zhengcheng.magic;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhangquansheng
 */
//@MapperScan("com.zhengcheng.magic.mapper")
@SpringBootApplication
public class MagicWebApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MagicWebApplication.class).run(args);
    }

}
