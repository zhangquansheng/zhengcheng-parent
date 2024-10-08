package com.zhengcheng.xxl.job;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.zhengcheng.xxl.job.properties.AppProperties;
import com.zhengcheng.xxl.job.properties.XxlJobExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;


/**
 * XxlJobAutoConfig
 *
 * @author :    quansheng.zhang
 * @date :    2019/8/5 15:19
 */
@Slf4j
@EnableConfigurationProperties({XxlJobExecutorProperties.class, AppProperties.class})
public class XxlJobAutoConfig {

    @Autowired
    private XxlJobExecutorProperties xxlJobExecutorProperties;
    @Autowired
    private AppProperties appProperties;

    @Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        Assert.notNull(xxlJobExecutorProperties.getAdminAddresses(), "xxl.job.executor.admin-addresses is not null");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobExecutorProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(StringUtils.isEmpty(xxlJobExecutorProperties.getAppName()) ? appProperties.getName() : xxlJobExecutorProperties.getAppName());
        xxlJobSpringExecutor.setIp(xxlJobExecutorProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobExecutorProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobExecutorProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(StringUtils.isEmpty(xxlJobExecutorProperties.getLogPath()) ? "/opt/logs/" + appProperties.getId() + "/applog/jobhandler" : xxlJobExecutorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobExecutorProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
