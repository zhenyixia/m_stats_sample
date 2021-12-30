package com.lyp.count.run.controller;

import com.lyp.count.common.bean.JsonResult;
import com.lyp.count.run.bean.QueryRunVO;
import com.lyp.count.run.bean.RunCountDetail;
import com.lyp.count.run.service.RunCountService;
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
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(path = "/run", produces = "application/json; charset=utf-8")
public class RunCountController{
  @Autowired
  RunCountService runCountService;

  @GetMapping(value = "hello")
  public JsonResult hello(){
    return JsonResult.success("Hello World!");
  }

  /**
   * 导入一周的运动信息
   *
   * @return JsonResult
   */
  @PostMapping(value = "import")
  public JsonResult importData(MultipartFile file){
    log.info("Begin to come in import data interface.");
    return runCountService.importData(file);
  }

  @PostMapping(value = "add")
  public JsonResult add(@RequestBody List<RunCountDetail> runCountVOs){
    return runCountService.add(runCountVOs);
  }

  @PostMapping(value = "list")
  public JsonResult list(@RequestBody QueryRunVO queryVO){
    return runCountService.getList(queryVO);
  }

  // http://127.0.0.1:8081/run/countWeek?weekIndex=0
  @GetMapping(value = "countInOneWeek")
  public JsonResult countWeek(@RequestParam(value = "weekIndex") int weekIndex){
    return runCountService.countWeek(weekIndex);
  }

  @PostMapping(value = "countInOneMonth")
  public JsonResult countInOneMonth(@RequestBody QueryRunVO queryVO){
    return runCountService.countInOneMonth(queryVO);
  }

  @PostMapping(value = "countInOneYear")
  public JsonResult countInOneYear(@RequestBody QueryRunVO queryVO){
    return runCountService.countInOneYear(queryVO);
  }

  @GetMapping(value = "countAllYears")
  public JsonResult countAllYears(){
    return runCountService.countAllYears();
  }


  @GetMapping("/exportFile")
  public void exportFile(HttpServletResponse response){

    String filename = "跑步导入模板.xlsx";
    Resource resource = new ClassPathResource("templates/" + filename);

    try(InputStream inputStream = resource.getInputStream();
        ServletOutputStream servletOutputStream = response.getOutputStream()){
      response.setContentType("application/vnd.ms-excel");
      response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.addHeader("charset", "utf-8");
      response.addHeader("Pragma", "no-cache");
      String encodeName = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
      response.setHeader("Content-Disposition",
          "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);

      IOUtils.copy(inputStream, servletOutputStream);
      response.flushBuffer();
    }catch(IOException e){
      e.printStackTrace();
    }
  }


  @GetMapping(value = "getYearMonthScope")
  public JsonResult getYearMonthScope(){
    return runCountService.getYearMonthScope();
  }

  @GetMapping(value = "getExistedAddress")
  public JsonResult getExistedAddress(){
    return runCountService.getExistedAddress();
  }
}