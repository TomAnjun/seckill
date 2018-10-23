package org.seckill.dao;

import org.seckill.entity.Seckill;
import org.seckill.util.ProtostuffUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * redis数据缓存dao
 */
public class RedisDao {
    private final JedisPool jedisPool = new JedisPool("127.0.0.1",6379);

    /**
     * 根据key获取redis缓存对象
     * @param key
     * @return
     */
    public Seckill getSeckill(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] bytes = jedis.get(ProtostuffUtil.serializer(key));
            if(bytes == null){
                return null;
            }else{
                Seckill seckill = ProtostuffUtil.deserializer(bytes,Seckill.class);
                return seckill;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 将对象缓存到redis缓存中
     * @param seckill
     * @return
     */
    public String setSeckill(Seckill seckill){
        Jedis jedis = jedisPool.getResource();
        try {
           byte[] bytes = ProtostuffUtil.serializer(seckill);
           String key = "seckillId:" + seckill.getSeckillId();
           String result = jedis.set(ProtostuffUtil.serializer(key),bytes);
           return result;
        } catch (Exception e) {
            e.printStackTrace();
        }   finally {
            jedis.close();
        }
        return null;
    }
}
