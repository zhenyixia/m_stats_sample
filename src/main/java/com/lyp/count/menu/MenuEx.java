package com.lyp.count.menu;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 菜单扩展
 *
 * @author Administrator
 * @since 2021/12/30 9:57
 **/
@Data
public class MenuEx extends Menu{

  private String subMenu;

  private String subMenuIdStr;

  private List<Map<String, String>> subMenuList = new ArrayList<>();

  public void setSubMenu(String subMenu){
    this.subMenu = subMenu;
    if(StringUtils.isNoneBlank(this.subMenu) && StringUtils.isNoneBlank(this.subMenuIdStr)){
      String[] subMenus = subMenu.split(",");
      String[] subMenuIds = this.subMenuIdStr.split(",");
      for(int i = 0; i < subMenus.length; i++){
        String subMenuId = subMenuIds[i];
        String subMenu1 = subMenus[i];
        this.subMenuList.add(ImmutableMap.of("name", subMenu1, "id", subMenuId));
      }
    }
  }

  public void setSubMenuIdStr(String subMenuIdStr){
    this.subMenuIdStr = subMenuIdStr;
    if(StringUtils.isNoneBlank(this.subMenu) && StringUtils.isNoneBlank(this.subMenuIdStr)){
      String[] subMenus = subMenu.split(",");
      String[] subMenuIds = this.subMenuIdStr.split(",");
      for(int i = 0; i < subMenus.length; i++){
        String subMenuId = subMenuIds[i];
        String subMenu1 = subMenus[i];
        this.subMenuList.add(ImmutableMap.of("name", subMenu1, "id", subMenuId));
      }
    }
  }
}