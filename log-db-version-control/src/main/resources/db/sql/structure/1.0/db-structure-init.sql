CREATE TABLE `test`
(
    `id`   varchar(30) NOT NULL COMMENT '主键',
    `name` varchar(30) COMMENT '是否默认,1是0否,默认0否',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB COMMENT='测试表';