package com.lyp.count.common.bean;

/**
 * 年月范围对象
 */
public class YearMonthScopeVO{
  private int maxYearMonth;

  private int minYearMonth;

  private int maxYear;

  private int minYear;

  public int getMaxYearMonth(){
    return maxYearMonth;
  }

  public void setMaxYearMonth(int maxYearMonth){
    this.maxYearMonth = maxYearMonth;
  }

  public int getMinYearMonth(){
    return minYearMonth;
  }

  public void setMinYearMonth(int minYearMonth){
    this.minYearMonth = minYearMonth;
  }

  public int getMaxYear(){
    return maxYear;
  }

  public void setMaxYear(int maxYear){
    this.maxYear = maxYear;
  }

  public int getMinYear(){
    return minYear;
  }

  public void setMinYear(int minYear){
    this.minYear = minYear;
  }
}