package com.lyp.count.run.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RunDetailVO{

  @NotNull
  private String address;

  @Pattern(regexp = "(\\d+)|(\\d+\\.\\d+)")
  private String kilometer;

  /**
   * 跑步日期，格式如： 2020-11-30
   */
  @Pattern(regexp = "^\\d{4}/\\d{1,2}/\\d{1,2}$",message = "跑步日期格式必须是：yyyy/M/d")
  private String runDate;

  /**
   * 跑步持续时长，格式为：10.34 表示10分34秒
   */
  @Pattern(regexp = "(\\d+)|(\\d+\\.\\d+)")
  private String lastTime;
}
