package com.lyp.count.learn.service.impl;

import com.lyp.count.common.bean.CountVO;
import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.common.bean.YearMonthScopeVO;
import com.lyp.count.common.exception.MyException;
import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.learn.bean.QueryLearnVO;
import com.lyp.count.learn.dao.LearnCountDao;
import com.lyp.count.learn.service.LearnCountService;
import com.lyp.count.learn.util.LearnUtils;
import com.lyp.count.run.bean.WeekDay;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
public class LearnCountServiceImpl implements LearnCountService{

  @Autowired
  LearnCountDao learnCountDao;

  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public JsonResult add(List<LearnCountDetail> countVOs){
    log.info("Begin to add run data.");
    if(CollectionUtils.isEmpty(countVOs)){
      log.warn("Input entry data is null.");
      return JsonResult.fail("录入数据不能为空");
    }

    for(LearnCountDetail runCountVO : countVOs){
      LearnUtils.setTimeInfo(runCountVO, runCountVO.getLearnDate());
    }
    int num = learnCountDao.batchInsert(countVOs);
    return JsonResult.success("成功添加条数为：" + num);
  }

  @Override
  public JsonResult getList(QueryLearnVO queryVO){
    int beginIndex = (queryVO.getPageNum() - 1) * queryVO.getPageSize();
    queryVO.setBeginIndex(beginIndex);

    List<LearnCountDetail> runCountDetails = learnCountDao.selectByCondition(queryVO);
    int total = learnCountDao.countByCondition(queryVO);

    Map<String, Object> result = new HashMap<>();
    result.put("list", runCountDetails);
    result.put("total", total);

    log.info("Page total:{}.", total);
    return JsonResult.success("查询成功", result);
  }

  @Override
  public JsonResult countWeek(int weekIndex, String learnContent){
    if(weekIndex > 0){
      return JsonResult.validFail("参数非法");
    }

    if("init".equals(learnContent)){
      List<String> scopeVO = learnCountDao.selectContent();
      learnContent = scopeVO.get(0);
    }

    // 获取当前周的周一的日期
    LocalDate currentWeekStartDay = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);

    // 获取上一周或几周的周一的日期
    LocalDate specialMonday = currentWeekStartDay.plusWeeks(weekIndex);
    LocalDate specialSunday = specialMonday.plusDays(6);
    List<LearnCountDetail> oneWeekCount = learnCountDao
        .selectSpecialWeek(specialMonday.format(dtf), specialSunday.format(dtf), learnContent);
    String fromMonthDay = MonthDay.from(specialMonday).format(DateTimeFormatter.ofPattern("MM-dd"));
    String toMonthDay = MonthDay.from(specialSunday).format(DateTimeFormatter.ofPattern("MM-dd"));

    BigDecimal totalKms = new BigDecimal(0);
    // List<Integer> units = new ArrayList<>();
    List<String> values = new ArrayList<>(Collections.nCopies(7, "0"));
    for(LearnCountDetail detailVO : oneWeekCount){
      totalKms = totalKms.add(detailVO.getLearnHours());
      String weekDay = detailVO.getWeekDay();
      int ordinal = WeekDay.nameOf(weekDay).ordinal();
      values.set(ordinal, detailVO.getLearnHours().toString());
    }

    totalKms.setScale(2, RoundingMode.HALF_UP);
    String weekDayScope = fromMonthDay + " ~ " + toMonthDay;
    CountVO response = new CountVO(null, values, totalKms.toString());
    response.setWeekDayScope(weekDayScope.replace("-", "/"));

    int totalNum = learnCountDao.countSpecialWeek(specialMonday.format(dtf), specialSunday.format(dtf), learnContent);
    response.setTotalTimes(totalNum);
    return JsonResult.success("查询成功", response);
  }

  @Override
  public JsonResult countInOneMonth(QueryLearnVO queryVO){
    if(queryVO == null){
      return JsonResult.validFail("传参不能为空");
    }

    Integer year = queryVO.getYear();
    Integer month = queryVO.getMonth();
    if(year == null || month == null){
      return JsonResult.validFail("年份和月份居必传");
    }
    String learnContent = queryVO.getLearnContent();
    if("init".equals(learnContent)){
      List<String> scopeVO = learnCountDao.selectContent();
      learnContent = scopeVO.get(0);
      queryVO.setLearnContent(learnContent);
    }

    List<LearnCountDetail> countVOS = learnCountDao.countByMonth(queryVO);
    try{
      CountVO countVO = LearnUtils.processMonthCount(countVOS);
      int totalTime = learnCountDao.selectTotalLearnTime(queryVO);
      countVO.setTotalTimes(totalTime);
      return JsonResult.success("按月统计成功！", countVO);
    }catch(MyException e){
      return JsonResult.fail(e.getMessage());
    }
  }

  @Override
  public JsonResult countInOneYear(QueryLearnVO queryVO){
    log.info("Begin to count by year.");
    if(queryVO == null || queryVO.getYear() == 0){
      return JsonResult.validFail("没有传年份");
    }

    List<LearnCountDetail> runCountDetails = learnCountDao.selectAllMonthByYear(queryVO);
    try{
      CountVO countVO = LearnUtils.processYearCount(runCountDetails);
      int totalTime = learnCountDao.selectTotalLearnTime(queryVO);
      countVO.setTotalTimes(totalTime);

      log.info("Count all month in one year successfully.");
      return JsonResult.success("按月统计成功！", countVO);
    }catch(MyException e){
      return JsonResult.fail(e.getMessage());
    }
  }

  @Override
  public JsonResult countAllYears(){
    List<LearnCountDetail> runCountDetails = learnCountDao.selectAlYearsData();
    CountVO countVO = LearnUtils.processAllYears(runCountDetails);

    log.info("Count all years successfully.");
    return JsonResult.success("统计所有年成功！", countVO);
  }

  @Override
  public JsonResult getYearMonthScope(){
    log.info("Begin to select year month scope.");
    YearMonthScopeVO scopeVO = learnCountDao.selectYearMonthScope();

    log.info("Query successfully.");
    return JsonResult.success("查询年月范围成功", scopeVO);
  }

  @Override
  public JsonResult getExistedContent(){
    log.info("Begin to select existed content.");
    List<String> scopeVO = learnCountDao.selectContent();

    log.info("Query successfully.");
    return JsonResult.success("查询年月范围成功", scopeVO);
  }
}