package com.lyp.count.common.flter;


import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

@WebFilter(filterName = "CorsFilter")
@Configuration
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Method", "POST,GET,PATCH,DELETE,PUT");
    response.setHeader("Access-Control-Max_Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-type,Accept");
    chain.doFilter(req, res);

  }
}
