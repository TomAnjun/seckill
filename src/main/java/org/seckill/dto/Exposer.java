package org.seckill.dto;

/**
 * 暴露秒杀接口地址
 */
public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //一种加密措施
    private  String md5;

    //id
    private long seckillId;

    //当前时间
    private long now;

    //开始时间
    private long startTime;

    //结束时间
    private long endTime;

    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public Exposer(boolean exposed, long seckillId, long now, long startTime, long endTime) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
