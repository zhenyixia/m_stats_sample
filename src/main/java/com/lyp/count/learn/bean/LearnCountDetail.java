package com.lyp.count.learn.bean;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LearnCountDetail{

  private Long id;

  @NotNull
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "跑步日期格式必须是：yyyy-MM-dd")
  private String learnDate;

  @NotNull
  @Pattern(regexp = "^\\d{2}\\.\\d{2}$", message = "学习开始时间格式必须是：HH.mm")
  private String beginTime;

  @NotNull
  @Pattern(regexp = "^\\d{2}\\.\\d{2}$", message = "学习结束时间格式必须是：HH.mm")
  private String endTime;

  private String learnContent;

  private BigDecimal learnHours;

  private int year;

  private int month;

  private int day;

  private String weekDay;
}
