package com.lyp.count.run.service;

import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.run.bean.QueryRunVO;
import com.lyp.count.run.bean.RunCountDetail;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface RunCountService{

  JsonResult getList(QueryRunVO queryVO);

  JsonResult add(List<RunCountDetail> runCountVOs);

  JsonResult count(QueryRunVO queryVO);

  JsonResult countWeek(int weekIndex);

  JsonResult importData(MultipartFile file);

  /**
   * 按月统计，统计某个月的，每一天数据
   *
   * @param queryRunVO 查询对象
   * @return JsonResult
   */
  JsonResult countInOneMonth(QueryRunVO queryRunVO);

  /**
   * 获取年月的范围
   *
   * @return JsonResult
   */
  JsonResult getYearMonthScope();

  /**
   * 按年统计，统计每年12个月的数据
   *
   * @param queryVO 查询对象
   * @return JsonResult
   */
  JsonResult countInOneYear(QueryRunVO queryVO);

  /**
   * 统计所有年的数据
   *
   * @return JsonResult
   */
  JsonResult countAllYears();

  /**
   * 获取已存在的跑步地址
   *
   * @return JsonResult
   */
  JsonResult getExistedAddress();
}