DROP TABLE tbl_run_detail;
CREATE TABLE `tbl_run_detail` (
 -- 跑步详情表
	`id` integer  primary KEY autoincrement, --id
	`kilometer` DECIMAL(9,2) , -- 本次跑步的公里数,
	`address` VARCHAR(100) , -- 运动地点
	`run_date` VARCHAR(15) , -- 跑步日期
	`year` INT(4) , -- 年，数据格式如：2021,
	`month` INT(2) , -- 月，数据格式为：1，2，...12,
	`day` INT(2) , -- 日,
	`week_day` VARCHAR(5) , -- 周几，数据范围为：一，二，...，六，日
	`run_second` INT(11) , -- 运动时长，单位为秒,
	`time_by_km` VARCHAR(15) , -- 平均配速，即每公里用时
	`km_by_hour` DECIMAL(3,1) , -- 平均速度，每小时跑多少公里，如6.7公里/时
	`update_time` datetime default (datetime('now', 'localtime'))  -- 记录更新时间,
);
CREATE INDEX run_detail_run_date ON tbl_run_detail(run_date);