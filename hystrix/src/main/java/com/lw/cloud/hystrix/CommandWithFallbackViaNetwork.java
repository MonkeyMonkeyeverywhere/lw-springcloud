package com.lw.cloud.hystrix;

import com.netflix.hystrix.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * fallback降级逻辑命令嵌套
 *
 * 适用场景:用于fallback逻辑涉及网络访问的情况,如缓存访问。
 * @author wei.liu
 */
public class CommandWithFallbackViaNetwork extends HystrixCommand<String> {

    private final int id;

    public CommandWithFallbackViaNetwork(int id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueCommand")));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        // RemoteService.getValue(id);
        throw new RuntimeException("force failure for example");
    }

    @Override
    protected String getFallback() {
        return new FallbackViaNetwork(id).execute();
    }

    private static class FallbackViaNetwork extends HystrixCommand<String> {

        private final int id;

        public FallbackViaNetwork(int id) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("RemoteServiceX"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("GetValueFallbackCommand"))
                    // 使用不同的线程池做隔离，防止上层线程池跑满，影响降级逻辑.
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceXFallback")));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
//            MemCacheClient.getValue(id);
            return "从缓存中取值";
        }

        @Override
        protected String getFallback() {
            return null;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String res = new CommandWithFallbackViaNetwork(1).execute();
        System.out.println(res);
        Future<String> future = new CommandWithFallbackViaNetwork(2).queue();
        System.out.println(future.get());
    }
}
