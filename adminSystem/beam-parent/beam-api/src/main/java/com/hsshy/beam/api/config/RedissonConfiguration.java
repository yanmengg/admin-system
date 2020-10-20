package com.hsshy.beam.api.config;
import com.hsshy.beam.api.config.properties.RedissonProperties;
import com.hsshy.beam.common.utils.redis.RedissLockUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

    @Autowired
    private RedissonProperties redssionProperties;

    /**
     * 集群模式自动装配
     * @return
     */
//    @Bean
//    @ConditionalOnProperty(name="redisson.cluster")
//    RedissonClient redissonCluster() {
//        Config config = new Config();
//
//        ClusterServersConfig clusterServersConfig = config.useClusterServers() //这是用的集群server
//                .setScanInterval(2000) //设置集群状态扫描时间
//                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize()) //设置连接数
//                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize())
//                .addNodeAddress(redssionProperties.getClusterAddresses());
//        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
//            clusterServersConfig.setPassword(redssionProperties.getPassword());
//        }
//        return Redisson.create(config);
//
//    }

    /**
     * 哨兵模式自动装配
     * @return
     */
//    @Bean
//    @ConditionalOnProperty(name="redisson.master-name")
//    RedissonClient redissonSentinel() {
//        Config config = new Config();
//        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redssionProperties.getSentinelAddresses())
//                .setMasterName(redssionProperties.getMasterName())
//                .setTimeout(redssionProperties.getTimeout())
//                .setMasterConnectionPoolSize(redssionProperties.getMasterConnectionPoolSize())
//                .setSlaveConnectionPoolSize(redssionProperties.getSlaveConnectionPoolSize());
//
//        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
//            serverConfig.setPassword(redssionProperties.getPassword());
//        }
//        return Redisson.create(config);
//    }

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="redisson.address")
    RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redssionProperties.getAddress())
                .setTimeout(redssionProperties.getTimeout())
                .setConnectionPoolSize(redssionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redssionProperties.getConnectionMinimumIdleSize());
        if(StringUtils.isNotBlank(redssionProperties.getPassword())) {
            serverConfig.setPassword(redssionProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    RedissLockUtil redissLockUtil(RedissonClient redissonClient) {
    	RedissLockUtil redissLockUtil = new RedissLockUtil();
    	redissLockUtil.setRedissonClient(redissonClient);
		return redissLockUtil;
    }

}
