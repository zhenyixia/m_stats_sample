package com.lyp.count.menu;

import com.lyp.count.common.bean.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单控制层
 *
 * @author Administrator
 * @since 2021/12/28 20:03
 **/
@RestController
@RequestMapping(path = "/menu", produces = "application/json; charset=utf-8")
public class MenuController{

  @Autowired
  MenuService menuService;

  @PostMapping(value = "getAll")
  public JsonResult getAll(){
    return menuService.getAllMenus();
  }

  @PostMapping(value = "getMenuTableData")
  public JsonResult getMenuTableData(@RequestBody QueryMenu queryMenu){
    return menuService.getMenuTableData(queryMenu);
  }

  @PostMapping(value = "add")
  public JsonResult add(@RequestBody Menu menu){
    return menuService.addMenu(menu.getMenu(), menu.getSubMenu());
  }

  @PostMapping(value = "delete")
  public JsonResult delete(@RequestParam long id){
    return menuService.deleteMenu(id);
  }

  @PostMapping(value = "batchDelete")
  public JsonResult batchDelete(@RequestParam(value = "ids") Long[] ids){
    return menuService.batchDeleteMenus(ids);
  }

  @PostMapping(value = "modify")
  public JsonResult modify(@RequestBody MenuMod menuMod){
    return menuService.modify(menuMod);
  }

  @PostMapping(value = "modifySub")
  public JsonResult modifySub(@RequestBody MenuMod menuMod){
    return menuService.modifySub(menuMod);
  }

  @GetMapping(value = "getLatestSubMenus")
  public JsonResult getLatestSubMenus(){
    return menuService.getLatestSubMenus();
  }
}