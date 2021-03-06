package com.lyp.count.learn.service;

import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.learn.bean.QueryLearnVO;
import java.util.List;

public interface LearnCountService{

  JsonResult add(List<LearnCountDetail> countVOs);

  JsonResult getList(QueryLearnVO queryVO);

  JsonResult countWeek(int weekIndex, String learnContent, Long menuId);

  JsonResult countInOneMonth(QueryLearnVO queryVO);

  JsonResult countInOneYear(QueryLearnVO queryVO);

  JsonResult countAllYears(QueryLearnVO menuId);

  JsonResult getYearMonthScope();

  JsonResult getAddressContent(Long menuId);
}
