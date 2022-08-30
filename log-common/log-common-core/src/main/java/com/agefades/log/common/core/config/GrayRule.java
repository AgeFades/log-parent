package com.agefades.log.common.core.config;

import cn.hutool.core.collection.CollUtil;
import com.agefades.log.common.core.constants.CommonConstant;
import com.agefades.log.common.core.util.GrayContextUtil;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Ribbon + Nacos 灰度切流逻辑实现类
 *
 * @author DuChao
 * @date 2022/8/3 10:08
 */
@Slf4j
@Configuration
public class GrayRule extends AbstractLoadBalancerRule {

    @Override
    public Server choose(Object key) {
        try {
            // 获取所有可用服务
            List<Server> serverList = this.getLoadBalancer().getReachableServers();
            if (CollUtil.isEmpty(serverList)) {
                log.error("当前注册中心没有可用服务");
                return null;
            }
            // 拆分为灰度服务与正常服务
            List<Instance> grayInstances = new ArrayList<>();
            List<Instance> normalInstances = new ArrayList<>();
            for (Server server : serverList) {
                NacosServer nacosServer = (NacosServer) server;
                String gray = nacosServer.getMetadata().get(CommonConstant.GRAY);
                Instance instance = nacosServer.getInstance();
                if (gray != null) {
                    grayInstances.add(instance);
                } else {
                    normalInstances.add(instance);
                }
            }

            Boolean grayTag = GrayContextUtil.getGrayTag();

            // 当灰度服务不为空 且 请求带灰度标记
            if (grayTag && CollUtil.isNotEmpty(grayInstances)) {
                log.info("当前使用灰度服务");
                return new NacosServer(ExtendBalancer.getHostByRandomWeight2(grayInstances));
            }
            // 其余走正常服务
            return new NacosServer(ExtendBalancer.getHostByRandomWeight2(normalInstances));
        } finally {
            GrayContextUtil.reset();
        }
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

}
