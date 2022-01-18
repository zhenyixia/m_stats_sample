package com.lyp.count.menu;

import lombok.Data;

/**
 * 菜单实体类
 *
 * @author Administrator
 * @since 2021/12/28 16:27
 **/
@Data
public class Menu{

  protected Long id;

  protected String menu;

  protected String subMenu;

  protected String content;

  protected String updateTime;
}