package com.lyp.count.run.util;

import com.lyp.count.common.exception.MyException;
import com.lyp.count.common.bean.CountVO;
import com.lyp.count.run.bean.RunCountDetail;
import com.lyp.count.run.bean.RunDetailVO;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Slf4j
public class SportUtils{
  private static final String LAST_TIME_REG = "^(\\d+)|((\\d+)(\\.)(\\d+))$";

  private static final Pattern lastTimePattern = Pattern.compile(LAST_TIME_REG);

  public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/M/d");

  public static List<RunCountDetail> convertRunCountDetail(List<RunDetailVO> detailVOS) throws MyException{
    List<RunCountDetail> details = new ArrayList<>();
    RunCountDetail runCountDetail;
    for(RunDetailVO detailVO : detailVOS){
      runCountDetail = new RunCountDetail();
      runCountDetail.setAddress(detailVO.getAddress());
      runCountDetail.setRunDate(detailVO.getRunDate());
      setTimeInfo(runCountDetail, detailVO.getRunDate());

      BigDecimal length = new BigDecimal(detailVO.getKilometer());
      runCountDetail.setKilometer(length);

      String lastTimeStr = detailVO.getLastTime();
      int lastSecond = convert2Second(lastTimeStr);
      runCountDetail.setRunSecond(lastSecond);

      BigDecimal useHour = new BigDecimal(lastSecond / 60.0 / 60).setScale(2, RoundingMode.HALF_UP);
      runCountDetail.setKmByHour(length.divide(useHour, 1, RoundingMode.HALF_UP));
      runCountDetail.setTimeByKm(useHour.divide(length, 1, RoundingMode.HALF_UP).toString());
      details.add(runCountDetail);
    }

    return details;
  }

  public static void setTimeInfo(RunCountDetail runCountDetail, String runDateStr){
    LocalDate runDate = LocalDate.parse(runDateStr, dtf);
    runCountDetail.setRunDate(runDate.toString());
    runCountDetail.setYear(runDate.getYear());
    runCountDetail.setMonth(runDate.getMonthValue());
    runCountDetail.setDay(runDate.getDayOfMonth());
    String weekday = runDate.getDayOfWeek().toString();
    runCountDetail.setWeekDay(WeekDay.valueOf(weekday).getName());
  }

  /**
   * 转化为秒数
   *
   * @param lastTimeStr 持续时间，格式为： 10分25秒 --> 625s
   */
  private static int convert2Second(String lastTimeStr) throws MyException{

    Matcher matcher = lastTimePattern.matcher(lastTimeStr);
    if(!matcher.matches()){
      throw new MyException("跑步时长格式有误，应该如:10.25，当前为：" + lastTimeStr);
    }

    String minStr = matcher.group(1);
    String secStr = matcher.group(3);
    int min = NumberUtils.toInt(minStr, 0);
    int sec = NumberUtils.toInt(secStr, 0);
    return min * 60 + sec;
  }

  public static void main(String[] args) throws MyException{
    /*String lastTime = "10分25秒";
    System.out.println(convert2Second(lastTime));*/
    String runDateStr = "2021/01/30";
    LocalDate runDate = LocalDate.parse(runDateStr, dtf);
    System.out.println(runDate);
    System.out.println(runDate.getDayOfWeek().getValue());
    WeekDay weekDay = WeekDay.valueOf(runDate.getDayOfWeek().toString());
    System.out.println(weekDay);
  }

  /**
   * 处理月度统计数据，返回当前月运动的天，如1，2，16，31，及对应每天的运动里程。并计算总和。
   *
   * @param countVOS 查询出的结果集
   * @return 统计对象
   * @throws MyException 异常
   */
  public static CountVO processMonthCount(List<RunCountDetail> countVOS) throws MyException{
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
    for(RunCountDetail countVO : countVOS){

      int day = countVO.getDay();
      int index = units.indexOf(day);
      if(index == -1){
        log.error("Run data had exception, id is:{}.", countVO.getId());
        throw new MyException("数据异常，请排查,id: {0}.", countVO.getId());
      }

      BigDecimal kilometer = countVO.getKilometer();
      totalKms = totalKms.add(kilometer);

      kmList.set(index, kilometer.toString());
    }

    return new CountVO(units, kmList, totalKms.setScale(1, RoundingMode.HALF_UP).toString());
  }

  /**
   * 处理按年统计，即统计某一年的所有月
   *
   * @param runCountDetails 运动数据，代表某一年的所有月
   * @return 一个统计对象
   */
  public static CountVO processYearCount(List<RunCountDetail> runCountDetails) throws MyException{
    Integer[] allMonth = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    List<Integer> units = Arrays.asList(allMonth);
    List<String> kmList = new ArrayList<>(Collections.nCopies(12, "0"));
    BigDecimal totalKms = new BigDecimal(0);
    for(RunCountDetail runCountDetail : runCountDetails){
      int month = runCountDetail.getMonth();
      BigDecimal kilometer = runCountDetail.getKilometer();
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

  public static CountVO processAllYears(List<RunCountDetail> runCountDetails){
    List<Integer> allYears = new ArrayList<>();
    List<String> kmList = new ArrayList<>();
    BigDecimal totalKms = new BigDecimal(0);
    for(RunCountDetail runCountDetail : runCountDetails){
      int year = runCountDetail.getYear();
      allYears.add(year);
      BigDecimal kilometer = runCountDetail.getKilometer();
      kmList.add(kilometer.toString());
      totalKms = totalKms.add(kilometer);
    }

    return new CountVO(allYears, kmList, totalKms.setScale(1, RoundingMode.HALF_UP).toString());
  }
}
