package com.zhengcheng.web;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhengcheng.web.config.ExceptionAdvice;
import com.zhengcheng.web.filter.MobileContextValueFilter;
import com.zhengcheng.web.properties.CustomMvcProperties;
import com.zhengcheng.web.util.AspectUtil;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Web模块自动配置
 *
 * @author :    quansheng.zhang
 * @date :    2019/1/26 7:59
 */
@Import({ExceptionAdvice.class})
@EnableConfigurationProperties({CustomMvcProperties.class})
public class WebAutoConfiguration {

    public WebAutoConfiguration() {
    }

    @Bean
    public HttpMessageConverters customConverters(CustomMvcProperties customMvcProperties) {
        Assert.notNull(customMvcProperties.getMobileMaskType(), "mobileMaskType is required");
        HttpMessageConverter<?> formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter<?> sourceHttpMessageConverter = new SourceHttpMessageConverter<>();
        //需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //下面这个contextType是需要添加；不然后面会报 * 不能匹配所有的contextType类型；
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        //处理中文乱码问题
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(supportedMediaTypes);
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect);
        fastJsonConfig.setSerializeFilters(new SerializeFilter[]{new MobileContextValueFilter(customMvcProperties.getMobileMaskType())});
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(sourceHttpMessageConverter, formHttpMessageConverter, converter);
    }

    @Bean
    public AspectUtil aspectUtil() {
        return new AspectUtil();
    }
}
