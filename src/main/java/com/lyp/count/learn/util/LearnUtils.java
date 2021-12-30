package com.lyp.count.learn.util;

import com.lyp.count.common.exception.MyException;
import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.common.bean.CountVO;
import com.lyp.count.run.bean.WeekDay;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

@Slf4j
public class LearnUtils{

  private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d");

  public static void setTimeInfo(LearnCountDetail learnCountDetail, String learnDate){
    LocalDate runDate = LocalDate.parse(learnDate, dtf);
    learnCountDetail.setLearnDate(runDate.toString());
    learnCountDetail.setMonth(runDate.getMonthValue());
    learnCountDetail.setYear(runDate.getYear());
    learnCountDetail.setDay(runDate.getDayOfMonth());
    String weekday = runDate.getDayOfWeek().toString();
    learnCountDetail.setWeekDay(WeekDay.valueOf(weekday).getName());
  }

  public static CountVO processMonthCount(List<LearnCountDetail> countVOS) throws MyException{
    LocalDate now = LocalDate.now();
    if(!CollectionUtils.isEmpty(countVOS)){
      now = LocalDate.of(countVOS.get(0).getYear(), countVOS.get(0).getMonth(), 1);
    }
    int length = now.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
    List<Integer> units = new ArrayList<>(length);
    for(int i = 1; i <= length; i++){
      units.add(i);
    }

    BigDecimal totalKms = new BigDecimal(0);
    List<String> kmList = new ArrayList<>(Collections.nCopies(length, "0"));
    for(LearnCountDetail countVO : countVOS){

      int day = countVO.getDay();
      int index = units.indexOf(day);
      if(index == -1){
        log.error("Run data had exception, id is:{}.", countVO.getId());
        throw new MyException("数据异常，请排查,id: {0}.", countVO.getId());
      }

      BigDecimal kilometer = countVO.getLearnHours();
      totalKms = totalKms.add(kilometer);

      kmList.set(index, kilometer.toString());
    }

    return new CountVO(units, kmList, totalKms.setScale(1, RoundingMode.HALF_UP).toString());
  }

  public static CountVO processYearCount(List<LearnCountDetail> runCountDetails) throws MyException{
    Integer[] allMonth = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    List<Integer> units = Arrays.asList(allMonth);
    List<String> kmList = new ArrayList<>(Collections.nCopies(12, "0"));
    BigDecimal totalKms = new BigDecimal(0);
    for(LearnCountDetail runCountDetail : runCountDetails){
      int month = runCountDetail.getMonth();
      BigDecimal kilometer = runCountDetail.getLearnHours();
      int index = units.indexOf(month);
      if(index == -1){
        log.error("Month illegal:{}.", month);
        throw new MyException("查询出的数据有误，月份不存在：{1}。", month);
      }

      kmList.set(index, kilometer.toString());
      totalKms = totalKms.add(kilometer);
    }

    return new CountVO(units, kmList, totalKms.setScale(1, RoundingMode.HALF_UP).toString());
  }

  public static CountVO processAllYears(List<LearnCountDetail> runCountDetails){
    List<Integer> allYears = new ArrayList<>();
    List<String> kmList = new ArrayList<>();
    BigDecimal totalKms = new BigDecimal(0);
    for(LearnCountDetail runCountDetail : runCountDetails){
      int year = runCountDetail.getYear();
      allYears.add(year);
      BigDecimal kilometer = runCountDetail.getLearnHours();
      kmList.add(kilometer.toString());
      totalKms = totalKms.add(kilometer);
    }

    return new CountVO(allYears, kmList, totalKms.setScale(1, RoundingMode.HALF_UP).toString());
  }
}