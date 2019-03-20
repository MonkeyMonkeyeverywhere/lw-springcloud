package com.lw.cloud.listener;

import com.lw.cloud.annotation.ApiRateLimit;
import com.lw.cloud.aspect.ApiLimitAspect;
import com.lw.cloud.util.ClasspathPackageScannerUtils;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;

/**
 * 初始化具体服务接口限流信息
 *
 * @author wei.liu
 */
public class InitApiLimitRateListener implements ApplicationListener<ApplicationPreparedEvent> {

    // controller包路径
    private String controllerPath;

    public InitApiLimitRateListener(String controllerPath) {
        this.controllerPath = controllerPath;
    }

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        try {
            initLimitRateAPI();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initLimitRateAPI() throws IOException, ClassNotFoundException {
        Object rate = Objects.isNull(System.getProperty("open.api.defaultLimit")) ? 100 : System.getProperty("open.api.defaultLimit");
        ApiLimitAspect.semaphoreMap.put("open.api.defaultLimit", new Semaphore(Integer.parseInt(rate.toString())));
        ClasspathPackageScannerUtils scan = new ClasspathPackageScannerUtils(controllerPath);
        List<String> classNameList = scan.getFullyQualifiedClassNameList();
        for(String clazz : classNameList){
            Class<?> clz = Class.forName(clazz);
            if(!clz.isAnnotationPresent(RestController.class)){
                continue;
            }
            Method[] methods = clz.getDeclaredMethods();
            for (Method method : methods) {
                if(method.isAnnotationPresent(ApiRateLimit.class)){
                    String confKey = method.getAnnotation(ApiRateLimit.class).confKey();
                    if(!StringUtils.isEmpty(confKey)){
                        ApiLimitAspect.semaphoreMap.put(confKey,new Semaphore(Integer.parseInt(System.getProperty(confKey))) );
                    }
                }
            }
        }
    }
}
