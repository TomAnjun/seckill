package org.seckill.service.impl;

import org.seckill.dao.RedisDao;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService {

    //日志
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    //盐值，加密时用
    private final static String slat = "faksljdfkasdjfkljasdf&^&*^&*^%@^&%^&$%#%$#,/.;";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 100);
    }

    public Seckill getSeckillById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        //先去缓存中查询，如果查询到直接显示，如果查询不懂，去数据库查询，然后放入缓存中
        Seckill seckill = redisDao.getSeckill("seckillId:" + seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            redisDao.setSeckill(seckill);
        }
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date ednTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > ednTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), ednTime.getTime());
        }
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "//" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {
        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存 + 记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                //没有更新到库存记录，秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    //秒杀成功
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw new SeckillCloseException(e1.getMessage());
        } catch (RepeatKillException e2) {
            throw new RepeatKillException(e2.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所有编译器异常转化为运行期异常
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
