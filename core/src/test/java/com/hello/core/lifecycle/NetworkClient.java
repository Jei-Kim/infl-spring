package com.hello.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/* 빈 생명주기 콜백
 * - 스프링이 초기화 작업과 종료 작업을 어떻게 진행하는지 알아보기 위한 예제 
 */
public class NetworkClient{

	 private String url;
	 public NetworkClient() { System.out.println("생성자 호출, url = " + url);
	 connect();
	 call("초기화 연결 메시지");
	 }
	 
	 public void setUrl(String url) {
		 this.url = url;
	 }
	       
	 //서비스 시작시 호출
	 public void connect() {
	       System.out.println("connect: " + url);
	  }
	 
	 public void call(String message) {
	       System.out.println("call: " + url + " message = " + message);
	 }
	 
	 //서비스 종료시 호출
	 public void disconnect() {
	           System.out.println("close: " + url);
	       }
	 
	 @PostConstruct
	 public void init() {
	 System.out.println("NetworkClient.init"); connect();
	 call("초기화 연결 메시지");
	 }
	 
	 @PreDestroy
	 public void close() {
	     System.out.println("NetworkClient.close");
	     disconnect();
	 }
	 
	 /*
	 // 설정 정보에 소멸 메서드 지정
	 public void close() {
         System.out.println("NetworkClient.close");
         diconnect();
}
*/
	 
	 /* 인터페이스 InitializingBean, DisposableBean

	 @Override
     public void afterPropertiesSet() throws Exception {
		 connect();
		 call("초기화 연결 메시지");
	 }
	 
     @Override
     public void destroy() throws Exception {
         disconnect();
     }
     */
}
