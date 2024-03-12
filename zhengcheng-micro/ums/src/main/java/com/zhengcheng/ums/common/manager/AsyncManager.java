package com.zhengcheng.ums.common.manager;


import java.util.concurrent.Executor;

import cn.hutool.extra.spring.SpringUtil;

/**
 * 异步任务管理器
 *
 * @author ruoyi
 */
public class AsyncManager {
    /**
     * 异步操作任务线程池
     */
    private Executor executor = SpringUtil.getBean("asyncTaskExecutor");

    /**
     * 单例模式
     */
    private AsyncManager() {
    }

    private static AsyncManager me = new AsyncManager();

    public static AsyncManager me() {
        return me;
    }

    /**
     * 执行任务
     *
     * @param task 任务
     */
    public void execute(Runnable task) {
        executor.execute(task);
    }

}
