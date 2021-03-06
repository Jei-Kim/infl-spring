package com.hello.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hello.core.discount.DiscountPolicy;
import com.hello.core.discount.RateDiscountPolicy;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemberService;
import com.hello.core.member.MemberServiceImpl;
import com.hello.core.member.MemoryMemberRepository;
import com.hello.core.order.OrderService;
import com.hello.core.order.OrderServiceImpl;

import ch.qos.logback.core.net.SyslogOutputStream;

@Configuration // 스프링으로 전환 - 설정정보를 담당하는 클래스임을 명시 
public class AppConfig {
	
	@Bean //스프링 컨테이너에 등록 
	//셍성자 주입
	public MemberService memberService() { 
		// MemberServiceImpl 만들고, 내가 만든 MemberServiceImpl는 MemoryMemberRepository를 사용할 것이다 
		System.out.println("call AppConfig.membeService");//로그 출력
		return new MemberServiceImpl(new MemoryMemberRepository());
	}
	
	@Bean
	// AppConfig 리팩토링하면서 추가함 - 역할 
	public MemberRepository memberRepository() {
		System.out.println("call AppConfig.memberRepositoory");//로그 출력
		return new MemoryMemberRepository();
	}
	
	@Bean
	public OrderService orderService() { 
		System.out.println("call AppConfig.orderService");//로그 출력
		return new OrderServiceImpl(memberRepository(), discountPolicy());
	}
	
	@Bean
	public DiscountPolicy discountPolicy() {
		//return new FixDiscountPolicy();
		 return new RateDiscountPolicy();
	}
}
