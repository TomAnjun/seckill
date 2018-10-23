-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
USE seckill;
-- 创建秒杀库存表
CREATE TABLE seckill (
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  ` NAME ` VARCHAR (120
) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开启时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time (start_time
),
  KEY idx_end_time (end_time
),
  KEY idx_create_time (create_time
)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT = '秒杀库存表';

--创建原始数据
INSERT INTO seckill
(name, number, start_time, end_time)
VALUES
  ('100元秒杀iphonexs', 100, '2018-09-27 14:39:00', '2018-09-28 14:39:00'),
  ('100元秒杀iphonexs max', 200, '2018-09-27 14:39:00', '2018-09-28 14:39:00'),
  ('100元秒杀iwatch', 1000, '2018-09-27 14:39:00', '2018-09-28 14:39:00');

-- 秒杀成功明细表
-- 用户登录认证相关信息
CREATE TABLE success_killed (
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  ` STATE ` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效，0：成功，1：已付款',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id, user_phone), /*联合主键*/
  KEY idx_create_time (create_time
)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '秒杀成功明细表';

-- 链接数据库控制台
mysql - uroot - p123456