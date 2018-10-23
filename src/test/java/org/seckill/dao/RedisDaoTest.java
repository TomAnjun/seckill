package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})

public class RedisDaoTest {

    @Autowired
    private RedisDao redisDao;

    @Test
    public void getSeckill() throws Exception {
        String key = "seckillId:" + 1001;
        Seckill seckill;
        seckill = redisDao.getSeckill(key);
        if(seckill == null){
            seckill = new Seckill();
            seckill.setNumber(20);
            seckill.setSeckillId(1001L);
            seckill.setStartTime(new Date());
            seckill.setEndTime(new Date());
            seckill.setCreateTime(new Date());
            redisDao.setSeckill(seckill);
        }else{
            System.out.println(seckill);
        }
    }

}