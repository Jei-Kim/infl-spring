package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/headers")
	 public String headers(HttpServletRequest request,
			 				HttpServletResponse response,
			 				HttpMethod httpMethod,
			 				Locale locale,
			 				@RequestHeader MultiValueMap<String, String> headermap,
			 				@RequestHeader("host") String host,
			 				@CookieValue(value = "myCookie", required = false) String cookie
	 ){
	 
	     log.info("request={}", request);
	     log.info("response={}", response);
	     log.info("httpMethod={}", httpMethod);
	     log.info("locale={}", locale);
	     log.info("headerMap={}", headermap);
	     log.info("header host={}", host);
	     log.info("myCookie={}", cookie);
	     
	     return "ok";
	 }
}
