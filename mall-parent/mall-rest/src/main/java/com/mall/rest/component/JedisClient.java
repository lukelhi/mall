package com.mall.rest.component;

public interface JedisClient {
    String set(String key, String value);
    String get(String key);
    Long expire(String key, int seconds);
    Long ttl(String key);//ttl返回长整形
    Long incr(String key);
    Long decr(String key);
    Long hset(String key, String item, String value);
    String hget(String key, String item);
    long hdel(String key, String item);
}
