package com.yhq.sensitive.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.yhq.sensitive.annotation.SensitiveInfo;
import com.yhq.sensitive.strategy.IStrategy;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Objects;

/**
 * 序列化实现类
 * @author yhq
 * @date 2021年9月6日 13点39分
 **/
@NoArgsConstructor
public class SensitiveInfoSerialize extends JsonSerializer<String> implements
        ContextualSerializer {

    /**
     * 脱敏策略
     */
    private IStrategy strategy;

    /**
     * 开始显示的字符长度
     */
    private int begin;

    /**
     * 结尾显示的字符长度
     */
    private int end;

    /**
     * 脱敏的正则
     */
    private String pattern;

    /**
     * 替换后的字符
     */
    private String replaceChar;

    public SensitiveInfoSerialize(IStrategy strategy, String pattern, String replaceChar,int begin,int end){
        this.strategy = strategy;
        this.pattern = pattern;
        this.replaceChar = replaceChar;
        this.begin = begin;
        this.end = end;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        /// 默认使用正则脱敏、 begin、end 不为空，则策略脱敏
        if(begin == 0 && end == 0){
            gen.writeString(strategy.desensitizationByPattern(value,pattern,replaceChar));
        }else{
            gen.writeString(strategy.desensitization(value,begin,end));
        }

    }

    @Override
    @SneakyThrows
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            // 非 String 类直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                SensitiveInfo sensitiveInfo = beanProperty.getAnnotation(SensitiveInfo.class);
                if (sensitiveInfo == null) {
                    sensitiveInfo = beanProperty.getContextAnnotation(SensitiveInfo.class);
                }
                if (sensitiveInfo != null) {
                    Class<? extends IStrategy> clazz = sensitiveInfo.strategy();
                    // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
                    return new SensitiveInfoSerialize(clazz.getDeclaredConstructor().newInstance(),sensitiveInfo.pattern(),
                            sensitiveInfo.replaceChar(),sensitiveInfo.begin(),sensitiveInfo.end());
                }
                return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
            }
        }
        return serializerProvider.findNullValueSerializer(null);
    }



}
