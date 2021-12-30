package com.lyp.count.run.controller;

import com.lyp.count.common.bean.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test", produces = "application/json; charset=utf-8")
public class HelloWorldController {

  @GetMapping(value = "hello")
  public JsonResult hello() {

    return JsonResult.success("hello world!");
  }
}
