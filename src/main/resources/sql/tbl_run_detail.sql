drop table if exists tbl_run_detail;
CREATE TABLE `tbl_run_detail` (
	`id` BIGINT(21) NOT NULL AUTO_INCREMENT,
	`kilometer` DECIMAL(9,2) NULL DEFAULT NULL COMMENT '本次跑步的公里数',
	`address` VARCHAR(100) NULL DEFAULT NULL COMMENT '运动地点',
	`run_date` varchar(15) NULL DEFAULT NULL COMMENT '跑步日期',
	`run_second` INT(11) NULL DEFAULT NULL COMMENT '运动时长，单位为秒',
	`time_by_km` VARCHAR(15) NULL DEFAULT NULL COMMENT '平均配速，即每公里用时',
	`km_by_hour` DECIMAL(3,1) NULL DEFAULT NULL COMMENT '平均速度，每小时跑多少公里，如6.7公里/时',
	`update_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
	PRIMARY KEY (`id`),
	index tbl_run_detail_run_date(run_date)
)
COMMENT='运动表'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=30
;