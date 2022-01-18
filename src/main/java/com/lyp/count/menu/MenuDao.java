package com.lyp.count.menu;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MenuDao{

  List<MenuEx> selectProcessedMenus();

  int addClass(@Param("menu") String menu, @Param("subMenu") String subMenu);

  List<Menu> selectMenuTableData(QueryMenu queryMenu);

  int countMenuTableData(QueryMenu queryMenu);

  int deleteMenuById(@Param("id") long id);

  int batchDeleteMenus(List<Long> asList);

  int updateMenu(MenuMod menuMod);

  int updateSubMenu(MenuMod menuMod);

  List<String> selectMenusAsc();

  List<Menu> selectLatestSubMenus();

  List<Menu> selectLatestContents();
}
