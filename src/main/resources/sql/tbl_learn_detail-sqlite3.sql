DROP TABLE tbl_learn_detail;
CREATE TABLE `tbl_learn_detail` (
 -- 学习详情表
	`id` integer  primary KEY autoincrement, --id
	`learn_date` VARCHAR(15) , -- 学习日期
	`begin_time` VARCHAR(15) , -- 学习开始时间
	`end_time` VARCHAR(15) , -- 学习结束时间
	`learn_content` VARCHAR(150) , -- 学习内容
	`learn_hours` DECIMAL(9,2) , -- 本次学习时长,
	`year` INT(4) , -- 年，数据格式如：2021,
	`month` INT(2) , -- 月，数据格式为：1，2，...12,
	`day` INT(2) , -- 日,
	`week_day` VARCHAR(5) , -- 周几，数据范围为：一，二，...，六，日
	`update_time` datetime default (datetime('now', 'localtime'))  -- 记录更新时间,
);
CREATE INDEX tbl_learn_detail_learn_date ON tbl_learn_detail(learn_date);