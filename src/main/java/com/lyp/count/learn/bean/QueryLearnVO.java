package com.lyp.count.learn.bean;

import com.lyp.count.common.bean.Page;
import java.util.Date;
import lombok.Data;

@Data
public class QueryLearnVO extends Page{

  private Long id;

  private String learnContent;

  private String learnDate;

  /**
   * 代表统计所有年
   */
  private String allYear;

  private Integer year;

  private Integer month;

  private Integer week;

  private Date createTime;
}
