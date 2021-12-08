package com.hello.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberService;
import com.hello.core.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

	AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

	@Test
	@DisplayName("빈 이름으로 조회")
	void finBeanByName() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		//Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // static import이
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	
	@Test
	@DisplayName("이름 없이 타입으로만 조회") // 간편하나 같은 타입이 여러 개 있을 시 문제 발생 
	void finBeanByType() {
		MemberService memberService = ac.getBean(MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
	}
	

	@Test
	@DisplayName("구체 타입으로 조회") 
	void finBeanByName2() {
		MemberService memberService = ac.getBean("memberService", MemberService.class);
		assertThat(memberService).isInstanceOf(MemberServiceImpl.class); // 11 import
	}
	
	@Test
	@DisplayName("빈 이름으로 조회x") 
	void finBeanByNameX() {
		//MemberService xxxx = ac.getBean("xxxx", MemberService.class);
		// 48을 실행하면 47이 성공(예외가 던져져야 함)헤야 함 
		assertThrows(NoSuchBeanDefinitionException.class,
				() -> ac.getBean("xxxx", MemberService.class)); // 12 import
	}
	
}
