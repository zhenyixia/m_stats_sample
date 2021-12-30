package com.lyp.count.run.constant;

public class Common{
  private Common(){
  }

  /**
   * 导入跑步excel的表头
   */
  public static final String[] RUN_HEADS = {"公里数", "地点", "跑步日期", "跑步时长"};

  /**
   * 导入跑步excel的表头对应的属性
   */
  public static final String[] RUN_HEAD_ATTRS = {"kilometer", "address", "runDate", "lastTime"};
}
