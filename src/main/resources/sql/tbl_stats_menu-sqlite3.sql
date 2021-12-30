drop table if exists tbl_stats_menu;
create table if not exists `tbl_stats_menu` (
	`id` integer  primary key autoincrement, --id
	`class` varchar(150) , --  '统计大类别，如学习，运动，。。。'
	`sub_class` varchar(150) , --  '统计小类，学习-》java学习；学习-》复习java，运动-》跑步'
	`update_time` datetime default (datetime('now', 'localtime'))
); --  '统计详情表'

CREATE INDEX tbl_stats_menu_class ON tbl_stats_menu(class);
CREATE INDEX tbl_stats_menu_sub_class ON tbl_stats_menu(sub_class);