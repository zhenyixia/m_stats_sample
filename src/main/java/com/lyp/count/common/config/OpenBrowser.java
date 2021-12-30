package com.lyp.count.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenBrowser implements CommandLineRunner{

  @Override
  public void run(String... args) throws Exception{
    log.info("应用已经准备就绪 ... 启动浏览器并自动加载指定的页面 ... ");
    try {
      Runtime.getRuntime().exec("cmd /c start http://localhost:8081/index.html#/countHome");//指定自己项目的路径
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}