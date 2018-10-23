package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * 配置junit和spring，从而使junit能够加载spring ioc容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        /**
         * Caused by: org.apache.ibatis.binding.BindingException: Parameter 'seckillId' not found. Available parameters are [0, 1, param1, param2]
         * 由于java在运行时不会保存形参的名称insertSuccessKilled(long seckillId, long userPhone)-》insertSuccessKilled(arg0, arg1)
         * 必须得告诉mybatis对应参数insertSuccessKilled(long seckillId, long userPhone)
         */
        /**
         * 测试结果：
         * 13:52:43.454 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@2a693f59] will not be managed by Spring
         * 13:52:43.495 [main] DEBUG o.s.d.S.insertSuccessKilled - ==>  Preparing: insert ignore into success_killed(seckill_id, user_phone) values (?, ?)
         * 13:52:43.605 [main] DEBUG o.s.d.S.insertSuccessKilled - ==> Parameters: 1000(Long), 17863002145(Long)
         * 13:52:43.702 [main] DEBUG o.s.d.S.insertSuccessKilled - <==    Updates: 1
         * 13:52:43.706 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@8519cb4]
         * insertCount:1
         */
        int insertCount = successKilledDao.insertSuccessKilled(1000L, 17863002145L);
        System.out.println("insertCount:" + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        /**
         * 测试结果：
         * 3:55:06.507 [main] DEBUG o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mchange.v2.c3p0.impl.NewProxyConnection@7fd50002] will not be managed by Spring
         * 13:55:06.553 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==>  Preparing: select sk.seckill_id, sk.user_phone, sk.create_time, sk.state, s.seckill_id "seckill.seckill_id", s.name "seckill.name", s.start_time "seckill.start_time", s.end_time "seckill.end_time", s.create_time "seckill.create_time" from success_killed sk inner join seckill s on sk.seckill_id = s.seckill_id where sk.seckill_id = ?
         * 13:55:06.580 [main] DEBUG o.s.d.S.queryByIdWithSeckill - ==> Parameters: 1000(Long)
         * 13:55:06.599 [main] DEBUG o.s.d.S.queryByIdWithSeckill - <==      Total: 1
         * 13:55:06.604 [main] DEBUG org.mybatis.spring.SqlSessionUtils - Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4b29d1d2]
         * SuccessKilled{
         * seckillId=1000,
         * userPhone=17863002145,
         * state=-1,
         * createTime=Sat Sep 29 13:52:43 CST 2018,
         * seckill=Seckill{
         *  seckillId=1000,
         *  name='100元秒杀iphonexs',
         *  number=0,
         *  startTime=Thu Sep 27 14:39:00 CST 2018,
         *  endTime=Fri Sep 28 14:39:00 CST 2018,
         *  createTime=Thu Sep 27 14:41:10 CST 2018}
         * }

         */
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000L, 17863002145L);
        System.out.println(successKilled);
    }

}