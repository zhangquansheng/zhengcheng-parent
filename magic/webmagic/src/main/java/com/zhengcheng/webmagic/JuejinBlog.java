package com.zhengcheng.webmagic;

import java.util.List;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * https://juejin.cn/
 *
 * @author quansheng1.zhang
 * @since 2024/2/20 11:26
 */
@TargetUrl("https://juejin.cn/post/\\d+")
public class JuejinBlog {

    @ExtractBy("//title")
    private String title;

    public static void main(String[] args) {
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), JuejinBlog.class).addUrl("https://juejin.cn/post").run();
    }

}
