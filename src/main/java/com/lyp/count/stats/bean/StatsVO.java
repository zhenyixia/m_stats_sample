package com.lyp.count.stats.bean;

import java.util.List;
import lombok.Data;

@Data
public class StatsVO{

  public StatsVO(List<String> valueList, String total){
    this.valueList = valueList;
    this.total = total;
  }

  /**
   * 统计类别
   */
  private String category;

  /**
   * 统计内容
   */
  private String content;

  /**
   * 统计单位
   */
  private String unit;

  /**
   * 按周统计，则为 一,二，三，。。。日
   * 按月则为 1,2,3,4,....
   */
  private List<String> dateList;

  /**
   * 与dateList对应，如果如果哪一天没有，则为”0"
   */
  private List<String> valueList;

  /**
   * 统计日期集内的总数，如一周跑步共10公里
   */
  private String total;

  /**
   * 统计日期集内的总次数，如一周跑步5次
   */
  private int totalTimes;

  /**
   * 一周，周一到周天的对应的时间范围，如：11/1 ~ 11/7
   */
  private String weekDayScope;

  public StatsVO(List<String> dateList, List<String> valueList, String total){
    this.dateList = dateList;
    this.valueList = valueList;
    this.total = total;
  }

  public StatsVO(){
  }
}
