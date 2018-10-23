package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和juint整合，junit启动时加载springIOC容器
 * spring-test，junit
 * 自动生成测试代码的快捷键是Ctrl+Shift+t
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date date = new Date();
        seckillDao.reduceNumber(1000L, date);

    }

    @Test
    public void queryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.toString());
        /**
         * Seckill{
         * seckillId=1000,
         * name='100元秒杀iphonexs',
         * number=100,
         * startTime=Thu Sep 27 14:39:00 CST 2018,
         * endTime=Fri Sep 28 14:39:00 CST 2018,
         * createTime=Thu Sep 27 14:41:10 CST 2018}
         */
    }

    @Test
    public void queryAll() throws Exception {
        /**
         * Caused by: org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
         * List<Seckill> queryAll(int offset, int limit)
         * 原因：java没有保存形参的名称：queryAll(int offset, int limit) -> queryAll(arg0, arg1)
         */
        /**
         * 测试结果：
         * Seckill{seckillId=1000, name='100元秒杀iphonexs', number=100, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}
         Seckill{seckillId=1001, name='100元秒杀iphonexs max', number=200, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}
         Seckill{seckillId=1002, name='100元秒杀iwatch', number=1000, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}
         */
        List<Seckill> seckills = seckillDao.queryAll(0, 100);
        for (Seckill seckill:
             seckills) {
            System.out.println(seckill);
        }
    }

}