package com.lyp.count.run.bean;

public enum WeekDay{
  MONDAY("一"), TUESDAY("二"), WEDNESDAY("三"), THURSDAY("四"), FRIDAY("五"), SATURDAY("六"), SUNDAY("日");

  private String name;

  WeekDay(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public static String[] names(){
    WeekDay[] values = WeekDay.values();
    String[] names = new String[values.length];
    for(int i = 0; i < values.length; i++){
      names[i] = values[i].getName();
    }
    return names;
  }

  public static WeekDay nameOf(String name){
    WeekDay[] values = WeekDay.values();
    for(WeekDay value : values){
      if(value.getName().equals(name)){
        return value;
      }
    }

    throw new IllegalArgumentException(name + "非法！");
  }

}