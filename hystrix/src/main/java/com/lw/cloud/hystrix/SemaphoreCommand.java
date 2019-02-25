package com.lw.cloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SemaphoreCommand extends HystrixCommand<String> {

    private String name;

    public SemaphoreCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        return "Hello " + name +" thread:" + Thread.currentThread().getName();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String res = new SemaphoreCommand("lw-cloud-semaphore").execute();
        System.out.println(res);
        Future<String> future = new SemaphoreCommand("lw-cloud-semaphore").queue();
        System.out.println(future.get());
    }
}
