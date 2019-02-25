package com.lw.cloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class CommandHelloWorld extends HystrixCommand<String> {

    private String name;

    public CommandHelloWorld(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(500)));
        this.name = name;
    }

    @Override
    protected String getFallback() {
        return "exeucute Falled" + Thread.currentThread().getName();
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String res = new CommandHelloWorld("lw-cloud").execute();
        System.out.println(res);
        Future<String> future = new CommandHelloWorld("lw-cloud").queue();
        System.out.println(future.get());
    }
}
