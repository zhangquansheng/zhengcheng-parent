package com.zhengcheng.rocket.mq.aspect;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.zhengcheng.common.message.BaseMessage;
import com.zhengcheng.rocket.mq.enums.ConsumeStatusEnum;
import com.zhengcheng.rocket.mq.properties.RocketMQDedupProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * {@link com.zhengcheng.rocket.mq.annotation.RocketMQDedup}  切面
 *
 * @author quansheng1.zhang
 * @since 2020/12/8 18:03
 */
@Slf4j
@ConditionalOnClass(StringRedisTemplate.class)
@EnableConfigurationProperties({RocketMQDedupProperties.class})
@Aspect
@Component
public class RocketMQDedupAspect {

    public final static String TRACE_ID = "X-ZM-BRAIN-ROCKET-MQ-TRACE-ID";

    public RocketMQDedupAspect() {
        log.info("--- RocketMQDedupAspect RocketMQ 消息去重，仅支持正常消息消费（MessageListener）、顺序消息消费（MessageOrderListener）-----");
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RocketMQDedupProperties rocketMQDedupProperties;


    @Pointcut(value = "@annotation(com.zhangmen.brain.solar.rocket.mq.annotation.RocketMQDedup)")
    public void annotationPointCut() {
    }

    @Around("annotationPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //只要有 Message 这个参数，那么就开始去重，否则不满足去重条件，例如：public Action consume(Message message, ConsumeContext context)
        Message message = null;
        boolean isNormal = false;
        boolean isOrder = false;
        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            if (arg instanceof Message) {
                message = (Message) arg;
            } else if (arg instanceof ConsumeContext) {
                isNormal = true;
            } else if (arg instanceof ConsumeOrderContext) {
                isOrder = true;
            }
        }
        if (Objects.isNull(message)) {
            return pjp.proceed();
        }

        // 是正常的消费消息
        String body = new String(message.getBody());
        log.info("topic: [{}] ,Receive tag:[{}] ,body:[{}]", message.getTopic(), message.getTag(), body);

        BaseMessage baseMessage = JSON.parseObject(body, BaseMessage.class);
        // 没有继承 BaseMessage，默认UUID生成dataId
        if (Objects.isNull(baseMessage) || StrUtil.isBlank(baseMessage.getDataId())) {
            return pjp.proceed();
        }

        long beginTime = System.currentTimeMillis();
        MDC.put(TRACE_ID, baseMessage.getDataId());
        log.info("dataId:[{}] prepare consume.", baseMessage.getDataId());

        // 插入去重表（消费中），带过期时间的
        String dedupKey = StrUtil.format("zm:brain:mq:dedup:key:{}", baseMessage.getDataId());
        Boolean execute = stringRedisTemplate.execute((RedisCallback<Boolean>)
                redisConnection ->
                        redisConnection.set(dedupKey.getBytes(),
                                (ConsumeStatusEnum.CONSUMING.getValue()).getBytes(),
                                Expiration.milliseconds(rocketMQDedupProperties.getProcessingExpire().toMillis()),
                                RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (Objects.nonNull(execute) && execute) {
            // 没有消费过
            try {
                // 业务代码（只有这块是你的业务）
                pjp.proceed();

                // 更新消息表状态为成功
                stringRedisTemplate.opsForValue().set(dedupKey, ConsumeStatusEnum.CONSUMED.getValue(), rocketMQDedupProperties.getRecordReserve().toMinutes(), TimeUnit.MINUTES);

                long costMs = System.currentTimeMillis() - beginTime;
                log.info("dataId:[{}] 消费成功 | 耗时：{}ms", baseMessage.getDataId(), costMs);
                //消费成功
                MDC.clear();

                return getSuccess(isNormal, isOrder);
            } catch (Exception e) {
                log.error("consume fail, message:{}, exceptionMessage:{}", body, e.getMessage(), e);
                // 删除消息表记录，消息重试
                stringRedisTemplate.delete(dedupKey);

                //消息重试
                return getSuspend(isNormal, isOrder);
            }
        } else {
            // 判断记录状态是否已成功
            String val = stringRedisTemplate.opsForValue().get(dedupKey);
            if (ConsumeStatusEnum.CONSUMING.getValue().equals(val)) {//正在消费中，稍后重试
                log.warn("the same message is considered consuming, try consume later dedupKey : {}", dedupKey);

                // 延迟消费
                return getSuspend(isNormal, isOrder);
            } else if (ConsumeStatusEnum.CONSUMED.getValue().equals(val)) {//证明消费过了，直接消费认为成功
                log.warn("message has been consumed before! dedupKey : {}, so just ack.", dedupKey);

                // 直接返回消费成功
                return getSuccess(isNormal, isOrder);
            } else {//非法结果，降级，直接消费
                log.warn("unknown consume result {}, ignore dedup, continue consuming,  dedupKey : {}", val, dedupKey);
                return getSuccess(isNormal, isOrder);
            }
        }
    }

    private Object getSuccess(boolean isNormal, boolean isOrder) {
        MDC.clear();
        if (isNormal && !isOrder) {
            return Action.CommitMessage;
        }
        return OrderAction.Success;
    }

    private Object getSuspend(boolean isNormal, boolean isOrder) {
        MDC.clear();
        if (isNormal && !isOrder) {
            return Action.ReconsumeLater;
        }
        return OrderAction.Suspend;
    }

}
