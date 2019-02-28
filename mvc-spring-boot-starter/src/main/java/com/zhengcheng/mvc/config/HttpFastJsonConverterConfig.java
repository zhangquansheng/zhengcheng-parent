package com.zhengcheng.mvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.zhengcheng.mvc.filter.MobileContextValueFilter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 为Spring Boot默认FASTJSON解析框架
 *
 * @author :    quansheng.zhang
 * @Filename :     HttpFastJsonConverterConfig.java
 * @Package :     com.zhengcheng.config
 * @Description :
 * @date :    2019/1/26 7:59
 */
@Configuration
@ComponentScan("com.zhengcheng.mvc.interceptor")
public class HttpFastJsonConverterConfig {

    @Bean
    public HttpMessageConverters customConverters() {
        HttpMessageConverter<?> formHttpMessageConverter = new FormHttpMessageConverter();
        HttpMessageConverter<?> sourceHttpMessageConverter = new SourceHttpMessageConverter<>();
        //需要定义一个Convert转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //WriteNullStringAsEmpty配置null值转换成空字符串； WriteNonStringValueAsString配置所有的值都加上双引号
        fastConverter.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNonStringValueAsString);
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
        //添加fastjson的配置信息，比如是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializeFilters(new MobileContextValueFilter());
        //3.在convert中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(sourceHttpMessageConverter, formHttpMessageConverter, converter);
    }
}
