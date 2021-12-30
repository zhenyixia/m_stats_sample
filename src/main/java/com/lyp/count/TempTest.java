package com.lyp.count;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.SerializationUtils;

/**
 * 临时测试类
 *
 * @author Administrator
 * @since 2021/6/27 12:54
 **/
public class TempTest{

  public static void main(String[] args){
    List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 6));
    List<Integer> list2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
    list1.retainAll(list2);
    System.out.println(list1);
    list2.retainAll(list1);
    System.out.println(list2);
  }
}