package com.zhengcheng.zk;

import com.zhengcheng.common.lock.DistributedLock;
import com.zhengcheng.zk.lock.ZkDistributedLock;
import com.zhengcheng.zk.properties.CuratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ZooKeeper 自动配置
 *
 * @author :    zhangquansheng
 * @date :    2020/5/29 14:57
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({CuratorProperties.class})
public class ZkAutoConfiguration {

    /**
     * initMethod start 相当于执行 client.start()
     *
     * @return CuratorFramework
     */
    @Bean(initMethod = "start")
    @ConditionalOnMissingBean
    public CuratorFramework curatorFramework(CuratorProperties curatorProperties) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(curatorProperties.getBaseSleepTimeMs(), curatorProperties.getMaxRetries());
        return CuratorFrameworkFactory.newClient(curatorProperties.getZookeeperConnectionString(), retryPolicy);
    }

    /**
     * ZK 分布式锁
     * zk.lock.enable = true
     *
     * @param curatorFramework 客户端
     * @return 分布式锁
     */
    @Bean
    @ConditionalOnProperty(name = "zk.lock.enable", havingValue = "true")
    public DistributedLock distributedLock(CuratorFramework curatorFramework) {
        if (log.isDebugEnabled()) {
            log.debug("ZK 分布式锁配置成功");
        }
        return new ZkDistributedLock(curatorFramework);
    }
}
