drop table tbl_stats_detail;
create table `tbl_stats_detail` (
	`id` bigint  primary key auto_increment,
	`category` varchar(150) comment '统计类别，如学习，运动，。。。' ,
	`content` varchar(150) comment '统计内容，类别的细分，如学习-多线程，运动-跑步' ,
	`address` varchar(150) comment '统计内容所在地址' ,
	`date` varchar(15) comment '统计日期，如：2021/11/01',
	`begin_time` varchar(15) comment '统计开始时间',
	`end_time` varchar(15) comment '统计结束时间',
	`unit` varchar(15) comment '统计单位，如小时，公里，次数，只',
	`number` decimal(9,2) comment '统计数，与统计单位配合，如0.5小时，0.5公里',
	`year` int(4)  comment '年，数据格式如：2021',
	`month` int(2)  comment '月，数据格式为：1，2，...12',
	`day` int(2) comment '日，数据格式为：1，2，...31',
	`week_day` varchar(5) comment '周几，数据范围为：一，二，...，六，日',
	`update_time` datetime default current_timestamp  on update current_timestamp,
	index category(`category`),
	index content(`content`),
	index address(`address`)
) comment '统计详情表';