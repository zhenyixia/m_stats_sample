package com.lyp.count.learn.controller;

import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.learn.bean.QueryLearnVO;
import com.lyp.count.learn.service.LearnCountService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/learn", produces = "application/json; charset=utf-8")
public class LearnCountController{
  @Autowired
  LearnCountService learnCountService;

  @GetMapping(value = "hello")
  public JsonResult hello(){
    return JsonResult.success("Hello World! Learning!");
  }

  @PostMapping(value = "add")
  public JsonResult add(@RequestBody List<LearnCountDetail> runCountVOs){
    return learnCountService.add(runCountVOs);
  }

  @PostMapping(value = "list")
  public JsonResult list(@RequestBody QueryLearnVO queryVO){
    return learnCountService.getList(queryVO);
  }

  // http://127.0.0.1:8081/run/countWeek?weekIndex=0
  @GetMapping(value = "countWeek")
  public JsonResult countWeek(@RequestParam(value = "weekIndex") int weekIndex,
      @RequestParam(value = "learnContent") String learnContent,@RequestParam(value = "menuId") Long menuId){
    return learnCountService.countWeek(weekIndex,learnContent,menuId);
  }

  @PostMapping(value = "countInOneMonth")
  public JsonResult countInOneMonth(@RequestBody QueryLearnVO queryVO){
    return learnCountService.countInOneMonth(queryVO);
  }

  @PostMapping(value = "countInOneYear")
  public JsonResult countInOneYear(@RequestBody QueryLearnVO queryVO){
    return learnCountService.countInOneYear(queryVO);
  }

  @PostMapping(value = "countAllYears")
  public JsonResult countAllYears(@RequestBody QueryLearnVO queryVO){
    return learnCountService.countAllYears(queryVO);
  }

  @GetMapping(value = "getYearMonthScope")
  public JsonResult getYearMonthScope(){
    return learnCountService.getYearMonthScope();
  }

  @GetMapping(value = "getAddressContent")
  public JsonResult getAddressContent(@RequestParam(value = "menuId") Long menuId){
    return learnCountService.getAddressContent(menuId);
  }
}