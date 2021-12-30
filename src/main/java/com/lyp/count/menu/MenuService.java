package com.lyp.count.menu;

import com.google.common.collect.ImmutableMap;
import com.lyp.count.common.bean.JsonResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 菜单业务类
 *
 * @author Administrator
 * @since 2021/12/28 20:02
 **/
@Service
public class MenuService{
  @Autowired
  MenuDao menuDao;

  public JsonResult getAllMenus(){

    List<MenuEx> menuList = menuDao.selectProcessedMenus();

    Map<String, List<Map<String, String>>> allMenuObjects = new HashMap<>();
    List<String> menus = new ArrayList<>();
    List<String> menuSubMenus = new ArrayList<>();

    menuList.forEach(menu -> {
      List<Map<String, String>> subMenuList = menu.getSubMenuList();
      allMenuObjects.put(menu.getMenu(), subMenuList);
      menus.add(menu.getMenu());
      menuSubMenus.add(menu.getMenu());
      if(!CollectionUtils.isEmpty(subMenuList)){
        List<String> subMenus = subMenuList.stream().map(map -> map.get("name")).collect(Collectors.toList());
        menuSubMenus.addAll(subMenus);
      }
    });

    return JsonResult.success("查询成功", ImmutableMap.of("menus", menus, "menuSubMenus", menuSubMenus, "allMenuObjects", allMenuObjects));
  }

  public JsonResult addMenu(String menu, String subMenu){
    int num = menuDao.addClass(menu, subMenu);
    return JsonResult.success("创建成功");
  }

  public JsonResult getMenuTableData(QueryMenu queryVO){
    int beginIndex = (queryVO.getPageNum() - 1) * queryVO.getPageSize();
    queryVO.setBeginIndex(beginIndex);
    List<Menu> menuList = menuDao.selectMenuTableData(queryVO);
    int total = menuDao.countMenuTableData(queryVO);

    return JsonResult.success("查询成功", ImmutableMap.of("list", menuList, "total", total));
  }

  public JsonResult deleteMenu(long id){
    int num = menuDao.deleteMenuById(id);
    return JsonResult.success("删除成功");
  }

  public JsonResult batchDeleteMenus(Long[] ids){
    int num = menuDao.batchDeleteMenus(Arrays.asList(ids));
    return JsonResult.success("删除成功");
  }

  public JsonResult modify(MenuMod menuMod){
    int num = menuDao.updateMenu(menuMod);
    return JsonResult.success("修改成功");
  }

  public JsonResult modifySub(MenuMod menuMod){
    int num = menuDao.updateSubMenu(menuMod);
    return JsonResult.success("修改成功");
  }
}