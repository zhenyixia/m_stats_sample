drop table if exists tbl_stats_detail;
create table if not exists `tbl_stats_detail` (
	`id` integer  primary key autoincrement, --id
	`menu_id` integer , --menu id
	`content` varchar(150) , --   -- 学习内容,如果 java-多线程
	`address` varchar(150) , --  '统计内容所在地址'
	`date` varchar(15) , --  '统计内容发生的日期，如：2021/11/01'
	`begin_time` varchar(15) , --  '统计开始时间'
	`end_time` varchar(15) , --  '统计结束时间'
	`total_time` INT(11) , -- 总时长，单位为秒,
	`year` int(4)  , --  '年，数据格式如：2021'
	`month` int(2)  , --  '月，数据格式为：1，2，...12'
	`day` int(2) , --  '日，数据格式为：1，2，...31'
	`week_day` varchar(5) , --  '周几，数据范围为：一，二，...，六，日'
	`update_time` datetime default (datetime('now', 'localtime'))
); --  '统计详情表'


CREATE INDEX tbl_stats_detail_menu_id ON tbl_stats_detail(menu_id);
CREATE INDEX tbl_stats_detail_content ON tbl_stats_detail(content);
CREATE INDEX tbl_stats_detail_address ON tbl_stats_detail(address);
CREATE INDEX tbl_stats_detail_date ON tbl_stats_detail(date);
CREATE INDEX tbl_stats_detail_week_day ON tbl_stats_detail(week_day);
CREATE INDEX tbl_stats_detail_total_time ON tbl_stats_detail(total_time);