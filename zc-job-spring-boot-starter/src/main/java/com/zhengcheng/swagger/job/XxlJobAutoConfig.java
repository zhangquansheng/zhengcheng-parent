package com.zhengcheng.swagger.job;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import com.zhengcheng.swagger.job.properties.AppProperties;
import com.zhengcheng.swagger.job.properties.XxlJobExecutorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;


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
        xxlJobSpringExecutor.setAppName(StringUtils.isEmpty(xxlJobExecutorProperties.getAppName()) ? appProperties.getName() : xxlJobExecutorProperties.getAppName());
        xxlJobSpringExecutor.setIp(xxlJobExecutorProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobExecutorProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobExecutorProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(StringUtils.isEmpty(xxlJobExecutorProperties.getLogPath()) ? "/opt/logs/" + appProperties.getId() + "/applog/jobhandler" : xxlJobExecutorProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobExecutorProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */

}
