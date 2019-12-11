-- 分8个数据表

DROP TABLE IF EXISTS `success_killed_0`;
CREATE TABLE `success_killed_0`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表0';

DROP TABLE IF EXISTS `success_killed_1`;
CREATE TABLE `success_killed_1`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表1';

DROP TABLE IF EXISTS `success_killed_2`;
CREATE TABLE `success_killed_2`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表2';

DROP TABLE IF EXISTS `success_killed_3`;
CREATE TABLE `success_killed_3`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表3';

DROP TABLE IF EXISTS `success_killed_4`;
CREATE TABLE `success_killed_4`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表4';

DROP TABLE IF EXISTS `success_killed_5`;
CREATE TABLE `success_killed_5`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表5';

DROP TABLE IF EXISTS `success_killed_6`;
CREATE TABLE `success_killed_6`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表6';

DROP TABLE IF EXISTS `success_killed_7`;
CREATE TABLE `success_killed_7`
(
    `seckill_id`  bigint(20) NOT NULL COMMENT '秒杀商品id',
    `user_id`     bigint(20) NOT NULL COMMENT '用户Id',
    `state`       tinyint(4) NOT NULL COMMENT '状态标示：-1指无效，0指成功，1指已付款',
    `create_time` timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`seckill_id`, `user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8 COMMENT ='秒杀成功明细表7';