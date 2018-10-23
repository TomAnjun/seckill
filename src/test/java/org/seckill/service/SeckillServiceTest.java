package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("seckillList={}", list);
        //seckillList=[Seckill{seckillId=1000, name='100元秒杀iphonexs', number=100, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}, Seckill{seckillId=1001, name='100元秒杀iphonexs max', number=200, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}, Seckill{seckillId=1002, name='100元秒杀iwatch', number=1000, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}]
    }

    @Test
    public void getSeckillById() throws Exception {
        long id = 1000L;
        Seckill seckill = seckillService.getSeckillById(id);
        logger.info("seckill={}", seckill);
        //Seckill{seckillId=1000, name='100元秒杀iphonexs', number=100, startTime=Thu Sep 27 14:39:00 CST 2018, endTime=Fri Sep 28 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}

    }

    @Test
    public void exportSeckillUrl(){
        long id = 1002L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            long phone = 14785223698L;
            String m5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, m5);
                logger.info("seckillExecution={}" ,seckillExecution);
            } catch (SeckillCloseException e) {
                logger.warn(e.getMessage());
            } catch (RepeatKillException e) {
                logger.warn(e.getMessage());
            }
        }else {
            logger.warn("exposer={}", exposer);
        }
        //exposer=Exposer{exposed=true, md5='d1ee29889a64b348b43d0c5e3bba376d', seckillId=1000, now=0, startTime=0, endTime=0}
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phone = 14785236985L;
        String md5 = "d1ee29889a64b348b43d0c5e3bba376d";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
        logger.info("seckillExecution={}", seckillExecution);
        //seckillExecution=SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功', successKilled=SuccessKilled{seckillId=1000, userPhone=14785236985, state=-1, createTime=Wed Oct 10 16:12:19 CST 2018, seckill=Seckill{seckillId=1000, name='100元秒杀iphonexs', number=0, startTime=Wed Oct 10 16:12:19 CST 2018, endTime=Thu Oct 25 14:39:00 CST 2018, createTime=Thu Sep 27 14:41:10 CST 2018}}}
    }

}