package com.lw.springcloud.rule;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 默认选择服务列表中的第一个服务
 */
public class MyRule implements IRule {

    private ILoadBalancer lb;

    @Override
    public Server choose(Object o) {
        List<Server> allServers = lb.getAllServers();
        allServers.forEach(s -> System.out.println(s.getHostPort()));
        return allServers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        lb = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return lb;
    }
}
