package com.lyp.count.common.bean;

import java.util.List;
import lombok.Data;

@Data
public class CountVO{
  /**
   * 单位集合，一月则为：1,2,3,...31等
   */
  private List<Integer> units;

  /**
   * 对应单位的统计值，如一周，则为：1.2,3.4,0,0,0,0,0等，对应上面的单位
   */
  private List<String> valueList;

  private String total;

  /**
   * 总运动次数
   */
  private int totalTimes;

  /**
   * 一周，周一到周天的时间范围
   */
  private String weekDayScope;

  public CountVO(List<Integer> units, List<String> valueList, String total){
    this.units = units;
    this.valueList = valueList;
    this.total = total;
  }
}
