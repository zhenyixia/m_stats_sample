package com.lyp.count.run.service.impl;

import com.lyp.count.common.bean.CountVO;
import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.common.bean.YearMonthScopeVO;
import com.lyp.count.common.exception.MyException;
import com.lyp.count.common.util.ExcelUtils;
import com.lyp.count.run.bean.QueryRunVO;
import com.lyp.count.run.bean.RunCountDetail;
import com.lyp.count.run.bean.RunDetailVO;
import com.lyp.count.run.bean.WeekDay;
import com.lyp.count.run.constant.Common;
import com.lyp.count.run.dao.RunCountDao;
import com.lyp.count.run.service.RunCountService;
import com.lyp.count.run.util.SportUtils;
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
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class RunCountServiceImpl implements RunCountService{
  @Autowired
  RunCountDao runCountDao;

  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public JsonResult getList(QueryRunVO queryVO){
    int beginIndex = (queryVO.getPageNum() - 1) * queryVO.getPageSize();
    queryVO.setBeginIndex(beginIndex);

    List<RunCountDetail> runCountDetails = runCountDao.selectByCondition(queryVO);
    int total = runCountDao.countByCondition(queryVO);

    Map<String, Object> result = new HashMap<>();
    result.put("list", runCountDetails);
    result.put("total", total);

    log.info("Page total:{}.", total);
    return JsonResult.success("查询成功", result);
  }

  @Override
  public JsonResult add(List<RunCountDetail> runCountVOs){
    log.info("Begin to add run data.");
    if(CollectionUtils.isEmpty(runCountVOs)){
      log.warn("Input entry data is null.");
      return JsonResult.fail("录入数据不能为空");
    }

    for(RunCountDetail runCountVO : runCountVOs){
      SportUtils.setTimeInfo(runCountVO, runCountVO.getRunDate());
    }
    int num = runCountDao.batchInsert(runCountVOs);
    return JsonResult.success("成功添加条数为：" + num);
  }

  @Override
  public JsonResult importData(MultipartFile file){
    if(Objects.isNull(file) || file.isEmpty()){
      return JsonResult.validFail("导入文件不能为空");
    }

    try{
      List<RunDetailVO> detailVOS = ExcelUtils.readList(file, 0, RunDetailVO.class, Common.RUN_HEADS, Common.RUN_HEAD_ATTRS);
      List<RunCountDetail> runDetails = SportUtils.convertRunCountDetail(detailVOS);

      int insertNum = runCountDao.batchInsert(runDetails);
      return JsonResult.success("成功导入，条数为：" + insertNum);
    }catch(MyException e){
      log.error("Batch import failed,msg:{}.", e.getMessage());
      return JsonResult.fail(e.getMessage());
    }
  }

  @Override
  public JsonResult count(QueryRunVO queryVO){
    return null;
  }

  @Override
  public JsonResult countWeek(int weekIndex){
    if(weekIndex > 0){
      return JsonResult.validFail("参数非法");
    }

    // 获取当前周的周一的日期
    LocalDate currentWeekStartDay = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);

    // 获取上一周或几周的周一的日期
    LocalDate specialMonday = currentWeekStartDay.plusWeeks(weekIndex);
    LocalDate specialSunday = specialMonday.plusDays(6);
    List<RunCountDetail> oneWeekCount = runCountDao.selectSpecialWeek(specialMonday.format(dtf), specialSunday.format(dtf));
    String fromMonthDay = MonthDay.from(specialMonday).format(DateTimeFormatter.ofPattern("MM-dd"));
    String toMonthDay = MonthDay.from(specialSunday).format(DateTimeFormatter.ofPattern("MM-dd"));

    BigDecimal totalKms = new BigDecimal(0);
    // List<Integer> units = new ArrayList<>();
    List<String> values = new ArrayList<>(Collections.nCopies(7, "0"));
    for(RunCountDetail detailVO : oneWeekCount){
      totalKms = totalKms.add(detailVO.getKilometer());
      String weekDay = detailVO.getWeekDay();
      int ordinal = WeekDay.nameOf(weekDay).ordinal();
      values.set(ordinal, detailVO.getKilometer().toString());
    }

    totalKms.setScale(2, RoundingMode.HALF_UP);
    String weekDayScope = fromMonthDay + " ~ " + toMonthDay;
    CountVO response = new CountVO(null, values, totalKms.toString());
    response.setWeekDayScope(weekDayScope.replace("-", "/"));

    int totalNum = runCountDao.countSpecialWeek(specialMonday.format(dtf), specialSunday.format(dtf));
    response.setTotalTimes(totalNum);
    return JsonResult.success("查询成功", response);
  }

  @Override
  public JsonResult countInOneMonth(QueryRunVO queryRunVO){
    if(queryRunVO == null){
      return JsonResult.validFail("传参不能为空");
    }

    Integer year = queryRunVO.getYear();
    Integer month = queryRunVO.getMonth();
    if(year == null || month == null){
      return JsonResult.validFail("年份和月份居必传");
    }

    List<RunCountDetail> countVOS = runCountDao.countByMonth(year, month);
    try{
      CountVO countVO = SportUtils.processMonthCount(countVOS);
      int totalTime = runCountDao.selectTotalRunTime(year, month);
      countVO.setTotalTimes(totalTime);
      return JsonResult.success("按月统计成功！", countVO);
    }catch(MyException e){
      return JsonResult.fail(e.getMessage());
    }
  }

  @Override
  public JsonResult getYearMonthScope(){

    log.info("Begin to select year month scope.");
    YearMonthScopeVO scopeVO = runCountDao.selectYearMonthScope();

    log.info("Query successfully.");
    return JsonResult.success("查询年月范围成功", scopeVO);
  }

  @Override
  public JsonResult countInOneYear(QueryRunVO queryVO){
    log.info("Begin to count by year.");
    if(queryVO == null || queryVO.getYear() == 0){
      return JsonResult.validFail("没有传年份");
    }

    List<RunCountDetail> runCountDetails = runCountDao.selectAllMonthByYear(queryVO);
    try{
      CountVO countVO = SportUtils.processYearCount(runCountDetails);
      int totalTime = runCountDao.selectTotalRunTime(queryVO.getYear(), null);
      countVO.setTotalTimes(totalTime);

      log.info("Count all month in one year successfully.");
      return JsonResult.success("按月统计成功！", countVO);
    }catch(MyException e){
      return JsonResult.fail(e.getMessage());
    }
  }

  @Override
  public JsonResult countAllYears(){
    List<RunCountDetail> runCountDetails = runCountDao.selectAlYearsData();
    CountVO countVO = SportUtils.processAllYears(runCountDetails);

    log.info("Count all years successfully.");
    return JsonResult.success("统计所有年成功！", countVO);
  }

  @Override
  public JsonResult getExistedAddress(){
    log.info("Begin to query existed address.");
    List<String> address = runCountDao.selectAddresses();
    return JsonResult.success("统计所有年成功！", address);
  }
}