package com.hello.core.common;

import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;
/*
@Component
@Scope(value = "request") //request 스코프로 지정 -> http 요청 당 하나씩 생성, 요청이 끝나는 시점에 소멸
*/

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

	private String uuid;
    private String requestURL;
    
    //requestURL은 빈이 생성되는 시점에 알 수 없으므로, 외부에서 setter로 입력받음 
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
}
    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "] " + message); }

    @PostConstruct //빈 생성 시점에 자동으로 uuid 생성, 저장 -> 다른 요청과 구분 가능 
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
}
    @PreDestroy //빈 소멸 시점에 종료 메세지를 남김 
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }

}
