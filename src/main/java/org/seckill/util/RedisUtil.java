package org.seckill.util;

import org.seckill.entity.RedisTest;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        System.out.println("redis connect success...");
        System.out.println("server is runing..." + jedis.ping());
        //设置 redis 字符串数据
        RedisTest redisTest = new RedisTest(12,"anjun");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }
}
