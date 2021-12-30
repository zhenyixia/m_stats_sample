package com.lyp.count.learn.dao;

import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.learn.bean.QueryLearnVO;
import com.lyp.count.common.bean.YearMonthScopeVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LearnCountDao{

  int batchInsert(List<LearnCountDetail> countVOs);

  List<LearnCountDetail> selectByCondition(QueryLearnVO queryVO);

  int countByCondition(QueryLearnVO queryVO);

  List<LearnCountDetail> countByMonth(QueryLearnVO queryVO);

  /**
   * 总的学习次数
   *
   * @param year 年
   * @param month 月
   * @return 次数
   */
  int selectTotalLearnTime(QueryLearnVO queryVO);

  List<LearnCountDetail> selectAllMonthByYear(QueryLearnVO queryVO);

  List<LearnCountDetail> selectAlYearsData();

  YearMonthScopeVO selectYearMonthScope();

  List<String> selectContent();

  List<LearnCountDetail> selectSpecialWeek(@Param("mondayDay") String mondayDay, @Param("sundayDay") String sundayDay, @Param("learnContent") String learnContent);

  int countSpecialWeek(@Param("mondayDay") String mondayDay, @Param("sundayDay") String sundayDay, @Param("learnContent") String learnContent);
}
