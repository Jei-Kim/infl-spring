package com.hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

	@Test
	void statefulServiceSingleton() {
		ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);

		
		//ThreadA: A사용자 10000원 주문
		statefulService1.order("userA", 10000);
		
		//ThreadB: B사용자 20000원 주문
		statefulService2.order("userB", 20000);
	
		//ThreadA: 사용자A 주문 금액 조회
		int price = statefulService1.getPrice();
		/*
		 * 20000이 나옴 -> 기대하는 결과x
		--> 중간에 21~22(B사용자의 주문건)이 들어왔기 때문, 
		--> 같은 인스턴스(price 필드)를 공유하기 때문에 인스턴스에 저장된 값이 출력된다.
		==> userAprice, userBprice와 같이 지역변수를 만들어서 각각 값을 담아주면 해결됨!
		 */
		System.out.println("price =" + price); 
		
		Assertions.assertThat(statefulService1.getPrice()).isEqualTo(200000);
	}
	
	static class TestConfig {
		
		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}
}
