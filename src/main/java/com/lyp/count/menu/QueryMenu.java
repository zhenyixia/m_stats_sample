package com.lyp.count.menu;

import com.lyp.count.common.bean.Page;
import lombok.Data;

@Data
public class QueryMenu extends Page{

  private String menu;

  private String subMenu;
}
