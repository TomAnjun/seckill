package org.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * 编译器异常  mybatis不回滚
 * 运行期异常  mybatis回滚
 */
public class RepeatKillException extends  SeckillException{
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
