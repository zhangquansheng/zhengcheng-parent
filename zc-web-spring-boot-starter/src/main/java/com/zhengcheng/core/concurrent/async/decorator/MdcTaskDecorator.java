package com.zhengcheng.core.concurrent.async.decorator;

import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;


/**
 * 解决异步执行时MDC内容延续的问题
 *
 * @author :    quansheng.zhang
 * @date :    2020/3/16 20:12
 */
@SuppressWarnings("NullableProblems")
public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        return new MdcContinueRunnableDecorator(runnable);
    }

    /**
     * 执行线程装饰器
     */
    protected static class MdcContinueRunnableDecorator implements Runnable {

        private final Runnable delegate;
        final Map<String, String> logContextMap;

        MdcContinueRunnableDecorator(Runnable runnable) {
            this.delegate = runnable;
            this.logContextMap = MDC.getCopyOfContextMap();
        }

        @Override
        public void run() {
            MDC.setContextMap(this.logContextMap);
            this.delegate.run();
            MDC.clear();
        }
    }
}