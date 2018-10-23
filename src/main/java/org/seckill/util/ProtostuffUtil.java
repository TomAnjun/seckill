package org.seckill.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.RedisTest;

/**
 * 序列化和反序列化工具类
 * 比java自带的序列化效率更高，占有空间更小
 */
public class ProtostuffUtil {
    /**
     * 将java对象序列化
     *
     */
    public static <T> byte[] serializer(T t){
        Schema schema = RuntimeSchema.getSchema(t.getClass());
        byte[] result = ProtobufIOUtil.toByteArray(t,schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return result;
    }

    /**
     * 将二进制数组进行反序列化
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {

        T obj = null;
        try {
            obj = clazz.newInstance();
            Schema schema = RuntimeSchema.getSchema(obj.getClass());
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static void main(String[] args) {
        RedisTest redisTest = new RedisTest(11,"anjun");
        byte[] bytes = ProtostuffUtil.serializer(redisTest);
        redisTest = ProtostuffUtil.deserializer(bytes,RedisTest.class);
        System.out.println(redisTest);
    }
}
