package com.lyp.count.run.bean;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RunCountDetail{

  private Long id;

  private BigDecimal kilometer;

  private String address;

  /**
   * 运动时长，单位为秒
   */
  private int runSecond;

  private int year;

  private int month;

  private int day;

  private String weekDay;

  /**
   * 平均速度，每小时跑多少公里，如：6.8
   */
  private BigDecimal kmByHour;

  /**
   * 平均配速，即每公里用时，如8分11秒每公里
   */
  private String timeByKm;

  @NotNull
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "跑步日期格式必须是：yyyy-MM-dd")
  private String runDate;
}
