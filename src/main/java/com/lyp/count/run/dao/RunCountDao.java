package com.lyp.count.run.dao;

import com.lyp.count.run.bean.QueryRunVO;
import com.lyp.count.run.bean.RunCountDetail;
import com.lyp.count.common.bean.YearMonthScopeVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RunCountDao{

  List<RunCountDetail> selectByCondition(QueryRunVO queryVO);

  int countByCondition(QueryRunVO queryVO);

  int batchInsert(List<RunCountDetail> runCountVOs);

  List<RunCountDetail> selectSpecialWeek(@Param("specialMonday") String specialMonday, @Param("specialSunday") String specialSunday);

  List<RunCountDetail> countByMonth(@Param("year") Integer year, @Param("month") Integer month);

  YearMonthScopeVO selectYearMonthScope();

  int selectTotalRunTime(@Param("year") Integer year, @Param("month") Integer month);

  List<RunCountDetail> selectAllMonthByYear(QueryRunVO queryVO);

  List<RunCountDetail> selectAlYearsData();

  List<String> selectAddresses();

  int countSpecialWeek(@Param("specialMonday") String specialMonday, @Param("specialSunday") String specialSunday);
}
