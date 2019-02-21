package com.lw.springcloud.listener;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRenewedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {

    @EventListener
    public void listen(EurekaInstanceCanceledEvent event){
        System.out.println(event.getServerId()+"----"+event.getAppName() +"服务下线！");
    }

    @EventListener(condition = "#event.replication==false")
    public void listen(EurekaInstanceRenewedEvent event){
        System.out.println(event.getServerId()+"----"+event.getAppName() +"服务续约！");
    }

    @EventListener(condition = "#event.replication==false")
    public void listen(EurekaInstanceRegisteredEvent event){
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println(instanceInfo.getAppName() +"服务注册！");
    }

}
