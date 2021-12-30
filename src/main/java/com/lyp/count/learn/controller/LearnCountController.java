package com.lyp.count.learn.controller;

import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.learn.bean.LearnCountDetail;
import com.lyp.count.learn.bean.QueryLearnVO;
import com.lyp.count.learn.service.LearnCountService;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
      @RequestParam(value = "learnContent") String learnContent){
    return learnCountService.countWeek(weekIndex,learnContent);
  }

  @PostMapping(value = "countInOneMonth")
  public JsonResult countInOneMonth(@RequestBody QueryLearnVO queryVO){
    return learnCountService.countInOneMonth(queryVO);
  }

  @PostMapping(value = "countInOneYear")
  public JsonResult countInOneYear(@RequestBody QueryLearnVO queryVO){
    return learnCountService.countInOneYear(queryVO);
  }

  @GetMapping(value = "countAllYears")
  public JsonResult countAllYears(){
    return learnCountService.countAllYears();
  }

  @GetMapping(value = "getYearMonthScope")
  public JsonResult getYearMonthScope(){
    return learnCountService.getYearMonthScope();
  }

  @GetMapping(value = "getExistedContent")
  public JsonResult getExistedContent(){
    return learnCountService.getExistedContent();
  }
}