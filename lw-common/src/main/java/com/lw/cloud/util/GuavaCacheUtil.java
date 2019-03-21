package com.lw.cloud.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存
 *
 * @author wei.liu
 */
public class GuavaCacheUtil {


    private static class CacheHolder {
        private final static RemovalListener<String, String> listener = notification -> System.out.println("[" + notification.getKey() + ":" + notification.getValue() + "] is removed!");

        private final static Cache<String,String> cache = CacheBuilder.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(1,TimeUnit.DAYS )
                .removalListener(listener)
                .recordStats() //开启统计信息开关
                .build();
    }

    private static Cache<String,String> getCache(){
        return CacheHolder.cache;
    }

    public static String get(String key) {
        return getCache().getIfPresent(key);
    }

    public static ImmutableMap<String, String> getAllPresent(Iterable<?> keys) {
        return getCache().getAllPresent(keys);
    }

    public static void put(String key, String value) {
        getCache().put(key, value);
    }

    public static void putAll(Map<? extends String, ? extends String> m) {
        getCache().putAll(m);
    }

    public static void invalidate(Object key) {
        getCache().invalidate(key);
    }

    public static void invalidateAll(Iterable<?> keys) {
        getCache().invalidateAll(keys);
    }

    public static void invalidateAll() {
        getCache().invalidateAll();
    }

    public static long size() {
        return getCache().size();
    }

    public ConcurrentMap<String, String> asMap() {
        return getCache().asMap();
    }

    public static void cleanUp() {
        getCache().cleanUp();
    }
}
