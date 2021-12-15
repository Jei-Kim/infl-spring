package com.hello.core.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hello.core.common.MyLogger;
import com.hello.core.logdemo.LogDemoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LogDemoController { // 로거 작동 확인용 컨트롤러 

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;
    //private final ObjectProvider<MyLogger> myLoggerProvider;
   
   @RequestMapping("log-demo") // requestURL 값 http://localhost:8080/log-demo
   @ResponseBody
   public String logDemo(HttpServletRequest request) {
	   
       String requestURL = request.getRequestURL().toString();
       
      // MyLogger myLogger = myLoggerProvider.getObject();
       
       myLogger.setRequestURL(requestURL);
       
       myLogger.log("controller test");
       logDemoService.logic("testId");
       return "OK";
   }
}
