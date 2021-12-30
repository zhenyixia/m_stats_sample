package com.lyp.count.run.bean;

import com.lyp.count.common.bean.Page;
import java.util.Date;
import lombok.Data;

@Data
public class QueryRunVO extends Page{

  private Long id;

  private String address;

  private String runDate;

  /**
   * 代表统计所有年
   */
  private String allYear;

  private Integer year;

  private Integer month;

  /**
   * -1代表上一周，-2代表上上周，0代表当前周
   */
  private Integer week;

  private Date createTime;
}
