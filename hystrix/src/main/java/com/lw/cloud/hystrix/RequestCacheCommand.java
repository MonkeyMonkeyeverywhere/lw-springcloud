package com.lw.cloud.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;

/**
 * 请求缓存 Request-Cache
 *
 * @author wei.liu
 */
public class RequestCacheCommand extends HystrixCommand<String> {

    private final int id;

    public RequestCacheCommand(int id) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheCommand"));
        this.id = id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    @Override
    protected String run() throws Exception {
        System.out.println(Thread.currentThread().getName()+" execute "+id);
        return "executed "+id;
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        RequestCacheCommand commanda = new RequestCacheCommand(2);
        RequestCacheCommand commandb = new RequestCacheCommand(2);
        System.out.println("----------commanda-----------");
        System.out.println(commanda.execute());
        boolean responseFromCacheA = commanda.isResponseFromCache();
        System.out.println("是否从缓存中取："+responseFromCacheA);
        System.out.println("----------commandb-----------");
        System.out.println(commandb.execute());
        boolean responseFromCacheB = commandb.isResponseFromCache();
        System.out.println("是否从缓存中取："+responseFromCacheB);
    }
}
